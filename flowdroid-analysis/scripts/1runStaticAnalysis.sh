#!/bin/bash

/usr/local/jdk1.8/bin/java -javaagent:/home/lmx/.local/share/JetBrains/Toolbox/apps/IDEA-U/ch-0/201.8538.31/lib/idea_rt.jar=42453:/home/lmx/.local/share/JetBrains/Toolbox/apps/IDEA-U/ch-0/201.8538.31/bin -Dfile.encoding=UTF-8 -classpath /usr/local/jdk1.8/jre/lib/charsets.jar:/usr/local/jdk1.8/jre/lib/deploy.jar:/usr/local/jdk1.8/jre/lib/ext/cldrdata.jar:/usr/local/jdk1.8/jre/lib/ext/dnsns.jar:/usr/local/jdk1.8/jre/lib/ext/jaccess.jar:/usr/local/jdk1.8/jre/lib/ext/jfxrt.jar:/usr/local/jdk1.8/jre/lib/ext/localedata.jar:/usr/local/jdk1.8/jre/lib/ext/nashorn.jar:/usr/local/jdk1.8/jre/lib/ext/sunec.jar:/usr/local/jdk1.8/jre/lib/ext/sunjce_provider.jar:/usr/local/jdk1.8/jre/lib/ext/sunpkcs11.jar:/usr/local/jdk1.8/jre/lib/ext/zipfs.jar:/usr/local/jdk1.8/jre/lib/javaws.jar:/usr/local/jdk1.8/jre/lib/jce.jar:/usr/local/jdk1.8/jre/lib/jfr.jar:/usr/local/jdk1.8/jre/lib/jfxswt.jar:/usr/local/jdk1.8/jre/lib/jsse.jar:/usr/local/jdk1.8/jre/lib/management-agent.jar:/usr/local/jdk1.8/jre/lib/plugin.jar:/usr/local/jdk1.8/jre/lib/resources.jar:/usr/local/jdk1.8/jre/lib/rt.jar:/home/lmx/Documents/GitHub/jpf-concolic/flowdroid-analysis/target/classes:/home/lmx/.m2/repository/org/soot-oss/soot/4.3.0/soot-4.3.0.jar:/home/lmx/.m2/repository/commons-io/commons-io/2.7/commons-io-2.7.jar:/home/lmx/.m2/repository/org/smali/dexlib2/2.5.2/dexlib2-2.5.2.jar:/home/lmx/.m2/repository/com/google/code/findbugs/jsr305/3.0.2/jsr305-3.0.2.jar:/home/lmx/.m2/repository/org/ow2/asm/asm/9.2/asm-9.2.jar:/home/lmx/.m2/repository/org/ow2/asm/asm-tree/9.2/asm-tree-9.2.jar:/home/lmx/.m2/repository/org/ow2/asm/asm-util/9.2/asm-util-9.2.jar:/home/lmx/.m2/repository/org/ow2/asm/asm-analysis/9.2/asm-analysis-9.2.jar:/home/lmx/.m2/repository/org/ow2/asm/asm-commons/9.2/asm-commons-9.2.jar:/home/lmx/.m2/repository/xmlpull/xmlpull/1.1.3.4d_b4_min/xmlpull-1.1.3.4d_b4_min.jar:/home/lmx/.m2/repository/de/upb/cs/swt/axml/2.1.2/axml-2.1.2.jar:/home/lmx/.m2/repository/ca/mcgill/sable/polyglot/2006/polyglot-2006.jar:/home/lmx/.m2/repository/de/upb/cs/swt/heros/1.2.3/heros-1.2.3.jar:/home/lmx/.m2/repository/org/functionaljava/functionaljava/4.2/functionaljava-4.2.jar:/home/lmx/.m2/repository/ca/mcgill/sable/jasmin/3.0.3/jasmin-3.0.3.jar:/home/lmx/.m2/repository/ca/mcgill/sable/java_cup/0.9.2/java_cup-0.9.2.jar:/home/lmx/.m2/repository/org/slf4j/slf4j-api/1.7.32/slf4j-api-1.7.32.jar:/home/lmx/.m2/repository/javax/annotation/javax.annotation-api/1.3.2/javax.annotation-api-1.3.2.jar:/home/lmx/.m2/repository/javax/xml/bind/jaxb-api/2.4.0-b180725.0427/jaxb-api-2.4.0-b180725.0427.jar:/home/lmx/.m2/repository/javax/activation/javax.activation-api/1.2.0/javax.activation-api-1.2.0.jar:/home/lmx/.m2/repository/org/glassfish/jaxb/jaxb-runtime/2.4.0-b180830.0438/jaxb-runtime-2.4.0-b180830.0438.jar:/home/lmx/.m2/repository/org/glassfish/jaxb/txw2/2.4.0-b180830.0438/txw2-2.4.0-b180830.0438.jar:/home/lmx/.m2/repository/com/sun/istack/istack-commons-runtime/3.0.7/istack-commons-runtime-3.0.7.jar:/home/lmx/.m2/repository/org/jvnet/staxex/stax-ex/1.8/stax-ex-1.8.jar:/home/lmx/.m2/repository/com/sun/xml/fastinfoset/FastInfoset/1.2.15/FastInfoset-1.2.15.jar:/home/lmx/.m2/repository/de/fraunhofer/sit/sse/flowdroid/soot-infoflow/2.10.0/soot-infoflow-2.10.0.jar:/home/lmx/.m2/repository/net/sf/trove4j/trove4j/3.0.3/trove4j-3.0.3.jar:/home/lmx/.m2/repository/com/google/guava/guava/31.0.1-jre/guava-31.0.1-jre.jar:/home/lmx/.m2/repository/com/google/guava/failureaccess/1.0.1/failureaccess-1.0.1.jar:/home/lmx/.m2/repository/com/google/guava/listenablefuture/9999.0-empty-to-avoid-conflict-with-guava/listenablefuture-9999.0-empty-to-avoid-conflict-with-guava.jar:/home/lmx/.m2/repository/org/checkerframework/checker-qual/3.12.0/checker-qual-3.12.0.jar:/home/lmx/.m2/repository/com/google/errorprone/error_prone_annotations/2.7.1/error_prone_annotations-2.7.1.jar:/home/lmx/.m2/repository/com/google/j2objc/j2objc-annotations/1.3/j2objc-annotations-1.3.jar featureDetection.SetParameters $1 $2 $3 $4 $5

