package com.testvagrant.parker.utilities.listeners;


import com.testvagrant.parker.utilities.readers.ConfigPropertyReader;
import org.testng.*;
import org.testng.collections.Lists;
import org.testng.internal.Utils;
import org.testng.xml.XmlSuite;

import java.io.*;
import java.text.DateFormat;
import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.text.SimpleDateFormat;
import java.util.*;

public class TestNGCustomReportListener implements IReporter {

    private PrintWriter writer;
    private int m_row;
    private Integer m_testIndex;
    private int m_methodIndex;
    private String reportTitle = "TestNG Customized Report";
    private String reportFileName = "AutomationReport.html";

    /**
     * Creates summary of the run
     */

    @Override
    public void generateReport(List<XmlSuite> xmlSuites, List<ISuite> suites, String outdir) {
        try {
            writer = createWriter("./screenshots");
        } catch (IOException e) {
            System.err.println("Unable to create output file");
            e.printStackTrace();
            return;
        }
        startHtml(writer);
        writeReportTitle(reportTitle);
        generateSuiteSummaryReport(suites);
        generateMethodSummaryReport(suites);
        //generateMethodDetailReport(suites);
        endHtml(writer);
        writer.flush();
        writer.close();
    }

    public void generateSuiteSummaryReport(List<ISuite> suites) {
        tableStart("testOverview", null);
        writer.print("<tr>");
        tableColumnStart("Test");
        tableColumnStart("Methods<br/>Passed");
        tableColumnStart("# Skipped");
        tableColumnStart("# Failed");
        tableColumnStart("Browser");
        tableColumnStart("Start Time");
        tableColumnStart("End Time");
        tableColumnStart("Total<br/>Time(hh:mm:ss)");
        tableColumnStart("Included<br/>Groups");
        tableColumnStart("Excluded<br/>Groups");

        writer.println("</tr>");
        NumberFormat formatter = new DecimalFormat("#,##0.0");
        int qty_tests = 0;
        int qty_pass_m = 0;
        int qty_pass_s = 0;
        int qty_skip = 0;
        long time_start = Long.MAX_VALUE;
        int qty_fail = 0;
        long time_end = Long.MIN_VALUE;
        m_testIndex = 1;
        for (ISuite suite : suites) {
            if (suites.size() >= 1) {
                titleRow(suite.getName(), 10);
            }
            Map<String, ISuiteResult> tests = suite.getResults();
            for (ISuiteResult r : tests.values()) {
                qty_tests += 1;
                ITestContext overview = r.getTestContext();
                startSummaryRow(overview.getName());
                int q = getMethodSet(overview.getPassedTests(), suite).size();
                qty_pass_m += q;
                summaryCell(q, Integer.MAX_VALUE);
                q = getMethodSet(overview.getSkippedTests(), suite).size();
                qty_skip += q;
                summaryCell(q, 0);
                q = getMethodSet(overview.getFailedTests(), suite).size();
                qty_fail += q;
                summaryCell(q, 0);
                // Write OS and Browser
                summaryCell(ConfigPropertyReader.getProperty("browser"), true);
                writer.println("</td>");
                SimpleDateFormat summaryFormat = new SimpleDateFormat("hh:mm:ss a");
                summaryCell(summaryFormat.format(overview.getStartDate()), true);
                writer.println("</td>");
                summaryCell(summaryFormat.format(overview.getEndDate()), true);
                writer.println("</td>");

                time_start = Math.min(overview.getStartDate().getTime(), time_start);
                time_end = Math.max(overview.getEndDate().getTime(), time_end);
                summaryCell(timeConversion((overview.getEndDate().getTime()
                        - overview.getStartDate().getTime()) / 1000), true);

                summaryCell(overview.getIncludedGroups());
                summaryCell(overview.getExcludedGroups());
                writer.println("</tr>");
                m_testIndex++;
            }
        }
        if (qty_tests > 1) {
            writer.println("<tr class=" + "total" + "><td>Total</td>");
            summaryCell(qty_pass_m, Integer.MAX_VALUE);
            summaryCell(qty_skip, 0);
            summaryCell(qty_fail, 0);
            summaryCell(" ", true);
            summaryCell(" ", true);
            summaryCell(" ", true);
            summaryCell(timeConversion(((time_end - time_start) / 1000)), true);
            writer.println("<td colspan=" + 3 + ">&nbsp;</td></tr>");
        }
        writer.println("</table>");
    }

    protected PrintWriter createWriter(String outdir) throws IOException {
        new File(outdir).mkdirs();
        return new PrintWriter(new BufferedWriter(new FileWriter(new File(outdir, reportFileName))));
    }

    /**
     * Creates a table showing the highlights of each test method with links to
     * the method details
     */
    protected void generateMethodSummaryReport(List<ISuite> suites) {
        m_methodIndex = 0;
        startResultSummaryTable("methodOverview");
        int testIndex = 1;
        for (ISuite suite : suites) {
            if (suites.size() >= 1) {
                titleRow(suite.getName(), 5);
            }
            Map<String, ISuiteResult> r = suite.getResults();
            for (ISuiteResult r2 : r.values()) {
                ITestContext testContext = r2.getTestContext();
                String testName = testContext.getName();
                m_testIndex = testIndex;
                resultSummary(suite, testContext.getFailedConfigurations(), testName, "Failed", " (configuration methods)");
                resultSummary(suite, testContext.getFailedTests(), testName, "Failed", "");
                resultSummary(suite, testContext.getSkippedConfigurations(), testName, "Skipped", " (configuration methods)");
                resultSummary(suite, testContext.getSkippedTests(), testName, "Skipped", "");
                resultSummary(suite, testContext.getPassedTests(), testName, "Passed", "");
                testIndex++;
            }
        }
        writer.println("</table>");
    }

    protected void writeReportTitle(String title) {
        writer.print("<center><h2>" + title + " - " + getDateAsString() + "</h2></center>");
    }

    /**
     * Starts HTML stream
     */
    protected void startHtml(PrintWriter out) {
        out.println("<!DOCTYPE html PUBLIC " + "-//W3C//DTD XHTML 1.1//EN" + "http://www.w3.org/TR/xhtml11/DTD/xhtml11.dtd" + ">");
        out.println("<html xmlns=" + "http://www.w3.org/1999/xhtml" + ">");
        out.println("<head>");
        out.println("<title>TestNG Report</title>");
        out.println("<style type=" + "text/css" + ">");
        out.println("table {margin-bottom:10px;border-collapse:collapse;empty-cells:show}");
        out.println("td,th {border:1px solid #009;padding:.25em .5em}");
        out.println(".result th {vertical-align:bottom}");
        out.println(".param th {padding-left:1em;padding-right:1em}");
        out.println(".param td {padding-left:.5em;padding-right:2em}");
        out.println(".stripe td,.stripe th {background-color: #E6EBF9}");
        out.println(".numi,.numi_attn {text-align:right}");
        out.println(".total td {font-weight:bold}");
        out.println(".passedodd td {background-color: #0A0}");
        out.println(".passedeven td {background-color: #3F3}");
        out.println(".skippedodd td {background-color: #CCC}");
        out.println(".skippedodd td {background-color: #DDD}");
        out.println(".failedodd td,.numi_attn {background-color: #F33}");
        out.println(".failedeven td,.stripe .numi_attn {background-color: #D00}");
        out.println(".stacktrace {white-space:pre;font-family:monospace}");
        out.println(".totop {font-size:85%;text-align:center;border-bottom:2px solid #000}");
        out.println("</style>");
        out.println("</head>");
        out.println("<body>");
    }

    /**
     * Finishes HTML stream
     */
    protected void endHtml(PrintWriter out) {
        out.println("<center> TestNG Report </center>");
        out.println("</body></html>");
    }

    /** Creates a section showing known results for each method */

    /**
     *
     */
    private void resultSummary(ISuite suite, IResultMap tests, String testname,
                               String style, String details) {
        if (tests.getAllResults().size() > 0) {
            StringBuffer buff = new StringBuffer();
            String lastClassName = "";
            int mq = 0;
            int cq = 0;
            for (ITestNGMethod method : getMethodSet(tests, suite)) {
                m_row += 1;
                m_methodIndex += 1;
                ITestClass testClass = method.getTestClass();
                String className = testClass.getName();
                if (mq == 0) {
                    String id = (m_testIndex == null ? null : "t"
                            + Integer.toString(m_testIndex));
                    titleRow(testname + " &#8212; " + style + details, 5, id);
                    m_testIndex = null;
                }
                if (!className.equalsIgnoreCase(lastClassName)) {
                    if (mq > 0) {
                        cq += 1;
                        writer.print("<tr class=" + style + (cq % 2 == 0 ? "even" : "odd") + ">" + "<td");
                        if (mq > 1) {
                            writer.print(" rowspan=" + mq);
                        }
                        writer.println(">" + lastClassName + "</td>" + buff);
                    }
                    mq = 0;
                    buff.setLength(0);
                    lastClassName = className;
                }
                Set<ITestResult> resultSet = tests.getResults(method);
                long end = Long.MIN_VALUE;
                long start = Long.MAX_VALUE;
                long startMS = 0;
                String firstLine = "";
                String screenshotLnk = "";
                for (ITestResult testResult : tests.getResults(method)) {
                    if (testResult.getEndMillis() > end) {
                        end = testResult.getEndMillis() / 1000;
                    }
                    if (testResult.getStartMillis() < start) {
                        startMS = testResult.getStartMillis();
                        start = startMS / 1000;
                    }
                    Throwable exception = testResult.getThrowable();
                    boolean hasThrowable = exception != null;
                    if (hasThrowable) {
                        @SuppressWarnings("deprecation")
                        String str = Utils.shortStackTrace(exception, true);
                        @SuppressWarnings("resource")
                        Scanner scanner = new Scanner(str);
                        firstLine = scanner.nextLine();
                        List<String> msgs = Reporter.getOutput(testResult);
                        boolean hasReporterOutput = msgs.size() > 0;
                        if (hasReporterOutput) {
                            for (String line : msgs) {
                                //out.println(line + "<br/>");
                                screenshotLnk += line + "<br/>";
                            }
                        }
                    }
                }
                DateFormat formatter = new SimpleDateFormat("hh:mm:ss");
                Calendar calendar = Calendar.getInstance();
                calendar.setTimeInMillis(startMS);
                mq += 1;
                if (mq > 1) {
                    buff.append("<tr class=" + style + (cq % 2 == 0 ? "odd" : "even") + ">");
                }
                String description = method.getDescription();
                String testInstanceName = resultSet
                        .toArray(new ITestResult[]{})[0].getTestName();
                buff.append("<td><a href=" + "#m" + m_methodIndex + ">" + qualifiedName(method) + " "
                        + (description != null && description.length() > 0 ? "(" + description + ")" : "") + "</a>"
                        + (null == testInstanceName ? "" : "<br>("
                        + testInstanceName + ")") + "</td>"
                        + "<td class=" + "numi" + " style=" + "text-align:left;padding-right:2em" + ">" + firstLine + "<br/>" + screenshotLnk + "</td>"
                        + "<td style=" + "text-align:right" + ">" + formatter.format(calendar.getTime()) + "</td>" + "<td class=" + "numi" + ">"
                        + timeConversion(end - start) + "</td>" + "</tr>");
            }
            if (mq > 0) {
                cq += 1;
                writer.print("<tr class=" + style + (cq % 2 == 0 ? "even" : "odd") + ">" + "<td");
                if (mq > 1) {
                    writer.print(" rowspan=" + mq);
                }
                writer.println(">" + lastClassName + "</td>" + buff);
            }
        }
    }

    private String timeConversion(long seconds) {
        final int MINUTES_IN_AN_HOUR = 60;
        final int SECONDS_IN_A_MINUTE = 60;
        int minutes = (int) (seconds / SECONDS_IN_A_MINUTE);
        seconds -= minutes * SECONDS_IN_A_MINUTE;
        int hours = minutes / MINUTES_IN_AN_HOUR;
        minutes -= hours * MINUTES_IN_AN_HOUR;
        return prefixZeroToDigit(hours) + ":" + prefixZeroToDigit(minutes) + ":" + prefixZeroToDigit((int) seconds);
    }

    private String prefixZeroToDigit(int num) {
        int number = num;
        if (number <= 9) {
            String sNumber = "0" + number;
            return sNumber;
        } else {
            return "" + number;
        }
    }

    /**
     * Starts and defines columns result summary table
     */
    private void startResultSummaryTable(String style) {
        tableStart(style, "summary");
        writer.println("<tr><th>Class</th>"
                + "<th>Method</th><th>Exception Info</th><th>Start Time </th><th>Execution Time<br/>(hh:mm:ss)</th></tr>");
        m_row = 0;
    }

    private String qualifiedName(ITestNGMethod method) {
        StringBuilder addon = new StringBuilder();
        String[] groups = method.getGroups();
        int length = groups.length;
        if (length > 0 && !"basic".equalsIgnoreCase(groups[0])) {
            addon.append("(");
            for (int i = 0; i < length; i++) {
                if (i > 0) {
                    addon.append(", ");
                }
                addon.append(groups[i]);
            }
            addon.append(")");
        }
        return "<b>" + method.getMethodName() + "</b> " + addon;
    }

    private Collection<ITestNGMethod> getMethodSet(IResultMap tests, ISuite suite) {
        List<IInvokedMethod> r = Lists.newArrayList();
        List<IInvokedMethod> invokedMethods = suite.getAllInvokedMethods();
        for (IInvokedMethod im : invokedMethods) {
            if (tests.getAllMethods().contains(im.getTestMethod())) {
                r.add(im);
            }
        }
        System.setProperty("java.util.Arrays.useLegacyMergeSort", "true");
        System.setProperty("java.util.Collections.useLegacyMergeSort", "true");
        //Collections.sort(r,new TestSorter());
        List<ITestNGMethod> result = Lists.newArrayList();

        // Add all the invoked methods
        for (IInvokedMethod m : r) {
            for (ITestNGMethod temp : result) {
                if (!temp.equals(m.getTestMethod()))
                    result.add(m.getTestMethod());
            }
        }

        // Add all the methods that weren't invoked (e.g. skipped) that we
        // haven't added yet
        Collection<ITestNGMethod> allMethodsCollection = tests.getAllMethods();
        List<ITestNGMethod> allMethods = new ArrayList<ITestNGMethod>(allMethodsCollection);
        //Collections.sort(allMethods, new TestMethodSorter());
        for (ITestNGMethod m : allMethods) {
            if (!result.contains(m)) {
                result.add(m);
            }
        }
        return result;
    }


    private void summaryCell(String[] val) {
        StringBuffer b = new StringBuffer();
        for (String v : val) {
            b.append(v + " ");
        }
        summaryCell(b.toString(), true);
    }

    private void summaryCell(String v, boolean isgood) {
        writer.print("<td class=" + "numi" + (isgood ? "" : "_attn") + ">" + v
                + "</td>");
    }

    private void startSummaryRow(String label) {
        m_row += 1;
        writer.print("<tr"
                + (m_row % 2 == 0 ? " class=" + "stripe" : "")
                + "><td style=" + "text-align:left;padding-right:2em" + "><a href=" + "#t" + ""
                + m_testIndex + "><b>" + label + "</b></a>" + "</td>");
    }

    private void summaryCell(int v, int maxexpected) {
        summaryCell(String.valueOf(v), v <= maxexpected);
    }

    private void tableStart(String cssclass, String id) {
        writer.println("<table cellspacing=" + 0 + " cellpadding=" + 0 + ""
                + (cssclass != null ? " class=" + "cssclass"
                : "style=" + "padding-bottom:2em")
                + (id != null ? " id=" + id : "") + ">");
        m_row = 0;
    }

    private void tableColumnStart(String label) {
        writer.print("<th>" + label + "</th>");
    }

    private void titleRow(String label, int cq) {
        titleRow(label, cq, null);
    }

    private void titleRow(String label, int cq, String id) {
        writer.print("<tr>");
        if (id != null) {
            writer.print("<br/>" + "<tr" + " id=" + id + ">");
        }
        writer.println("<th colspan=" + cq + ">" + label + "</th></tr>");
        m_row = 0;
    }

    /*
     * Method to get Date as String
     */
    private String getDateAsString() {
        DateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss");
        Date date = new Date();
        return dateFormat.format(date);
    }


}
