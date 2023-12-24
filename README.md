# jset


This Github site contains the artifact of the submission titled "JSET: A Benchmark for Java Symbolic Execution" in ISSTA 2024. We provide this repository to assist the reviewers in evaluating our work. 

The artifact contains source code, scripts and experimental results of our submission. The reviewers can follow the steps below to set the environment and try our work. 

# Benchmark Component
Each program in JSET provides source code and at least one symbolic execution driver, which are. jpf files for the two open-source tools SPF and JDart.


# Experiment Environment

The installation process of SPF and JDart can refer to the official website of the tool: https://github.com/SymbolicPathFinder/jpf-symbc for SPF and https://github.com/psycopaths/jdart for JDart.


# Symbolic Execution of SPF and JDart

Running scripts runTool1SPFPara.py and runTool2JDartPara.py can run all symbolic execution drivers of JSET and obtain the running results.

Running script run_coverage_para.py can run all coverage drivers and obtain the coverage results.

# Experiment Data

The detailed results (average value of three runs) of path explored under three tools will be displayed.

```bash
+------------------+------------+-----------+----------------+
|                             Path                           |
+------------------+------------+-----------+----------------+
|  Program         |	SPF     |  JDart    |  JPF-Concolic  |
+------------------+------------+-----------+----------------+
|  Actson          |	12686   |  0        |  0             |
|  Argo            |	0       |  0        |  17704         |
|  Commonmark      |	59048   |  14446    |  17518         |
|  CommonsCsv      |	6837    |  15263    |  18043         |
|  Expression      |	0       |  14802    |  0             |
|  fastCsv         |	2454    |  15322    |  19841         |
|  Flexmark        |	0       |  0        |  13269         |
|  Foxykeep        |	0       |  0        |  11557         |
|  Gson            |	0       |  16153    |  19361         |
|  html5Parser     |	0       |  0        |  1067          |
|  Htmlcleaner     |	288     |  141      |  957           |
|  InoJava         |	0       |  13507    |  0             |
|  Jcsv            |	218418  |  17999    |  19001         |
|  Jericho         |	0       |  0        |  18172         |
|  jlex            |	0       |  0        |  104945        |
|  Jmp123          |	59      |  0        |  0             |
|  Jpat            |	872     |  0        |  78586         |
|  JsonFlattener   |	0       |  0        |  8714          |
|  Jsoniter        |	0       |  0        |  19320         |
|  JsonJava        |	257     |  0        |  0             |
|  Jsoup           |	95      |  4538     |  1879          |
|  Jtidy           |	0       |  10212    |  0             |
|  Jurl            |	0       |  0        |  16619         |
|  Markdown4j      |	19      |  18410    |  18123         |
|  MarkdownPapers  |	32      |  16600    |  0             |
|  MinimalJson     |	245534  |  14383    |  18423         |
|  nanojson        |	32199   |  14098    |  16198         |
|  nanoxml         |	25593   |  14535    |  42107         |
|  Pobs            |	651     |  0        |  43527         |
|  Rhino           |	0       |  0        |  84505         |
|  sablecc         |	0       |  0        |  1090          |
|  sie             |	161     |  0        |  17935         |
|  simpleCsv       |	242     |  14474    |  20022         |
|  snakeyaml       |	0       |  0        |  2084          |
|  supserCsv       |	242     |  14692    |  20334         |
|  Txtmark         |	0       |  14380    |  0             |
|  Univocity       |	0       |  14300    |  19578         |
|  urlDetector     |	193470  |  15656    |  19524         |
|  xml             |	10      |  21206    |  41081         |
+------------------+------------+-----------+----------------+
```

The detailed results of branch coverage under three tools will be displayed.

```bash
+-------------------------------------------------------+
|                          Cov                          |
+------------------+--------+----------+----------------+
|  Program         |  SPF   |  JDart   |  JPF-Concolic  |
+------------------+--------+----------+----------------+
|  Actson          |  0.0   |  0.0     |  0.0           |
|  Argo            |  0.0   |  0.0     |  0.2541        |
|  Commonmark      |  0.08  |  0.2187  |  0.3848        |
|  CommonsCsv      |  0.3   |  0.062   |  0.062         |
|  Expression      |  0.0   |  0.3177  |  0.0           |
|  fastCsv         |  0.53  |  0.4286  |  0.4286        |
|  Flexmark        |  0.0   |  0.0     |  0.0966        |
|  Foxykeep        |  0.0   |  0.0     |  0.127         |
|  Gson            |  0.0   |  0.1096  |  0.0836        |
|  html5Parser     |  0.0   |  0.0     |  0.0102        |
|  Htmlcleaner     |  0.04  |  0.159   |  0.1509        |
|  InoJava         |  0.0   |  0.0815  |  0.0           |
|  Jcsv            |  0.04  |  0.1517  |  0.2483        |
|  Jericho         |  0.0   |  0.0     |  0.0471        |
|  jlex            |  0.0   |  0.0     |  0.0           |
|  Jmp123          |  0.19  |  0.0     |  0.0           |
|  Jpat            |  0.52  |  0.0     |  0.0755        |
|  JsonFlattener   |  0.0   |  0.0     |  0.1957        |
|  Jsoniter        |  0.0   |  0.0     |  0.023         |
|  JsonJava        |  0.01  |  0.0     |  0.0           |
|  Jsoup           |  0.04  |  0.1268  |  0.1364        |
|  Jtidy           |  0.0   |  0.1185  |  0.0           |
|  Jurl            |  0.0   |  0.0     |  0.2817        |
|  Markdown4j      |  0.03  |  0.0909  |  0.0909        |
|  MarkdownPapers  |  0.01  |  0.2801  |  0.0           |
|  MinimalJson     |  0.11  |  0.4579  |  0.2022        |
|  nanojson        |  0.1   |  0.3674  |  0.1772        |
|  nanoxml         |  0.37  |  0.3529  |  0.1875        |
|  Pobs            |  0.29  |  0.0     |  0.3128        |
|  Rhino           |  0.0   |  0.0     |  0.0251        |
|  sablecc         |  0.0   |  0.0     |  0.0           |
|  sie             |  0.65  |  0.0     |  0.7018        |
|  simpleCsv       |  0.06  |  0.0024  |  0.0024        |
|  snakeyaml       |  0.0   |  0.0     |  0.0181        |
|  supserCsv       |  0.07  |  0.0604  |  0.1152        |
|  Txtmark         |  0.0   |  0.2282  |  0.0           |
|  Univocity       |  0.0   |  0.0582  |  0.0642        |
|  urlDetector     |  0.06  |  0.3962  |  0.2243        |
|  xml             |  0.36  |  0.7287  |  0.2558        |
+------------------+--------+----------+----------------+
```
