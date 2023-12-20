package test;

//import gov.nasa.jpf.jdart.Debug;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileReader;
import java.io.InputStreamReader;
import java.io.Reader;
import java.io.StringReader;
import java.util.ArrayList;

import org.json.JSONObject;

import com.foxykeep.cpcodegenerator.FileCache;
import com.foxykeep.cpcodegenerator.generator.DatabaseGenerator;
import com.foxykeep.cpcodegenerator.model.TableData;
import com.foxykeep.cpcodegenerator.util.PathUtils;

public class TestFoxykeep {
	public static void main(String[] args) throws Exception{

        final File fileInputDir = new File("src/example-foxykeep/input");
        if (!fileInputDir.exists() || !fileInputDir.isDirectory()) {
            return;
        }

        String columnMetadataText;
        final StringBuilder sb = new StringBuilder();
        BufferedReader br;

        br = new BufferedReader(new FileReader(new File("src/example-foxykeep/res/column_metadata.txt")));
        String line;
        while ((line = br.readLine()) != null) {
            sb.append(line).append("\n");
        }
        columnMetadataText = sb.toString();

        File file=new File("src/example-foxykeep/input/sample.json");
        final String fileName = file.getName();
        System.out.println("Generating code for " + fileName);

        final char[] buffer = new char[2048];
        sb.setLength(0);
        final Reader in;

        in = new InputStreamReader(new FileInputStream(file), "UTF-8");
        int read;
        do {
            read = in.read(buffer, 0, buffer.length);
            if (read != -1) {
                sb.append(buffer, 0, read);
            }
        } while (read >= 0);

        final String content = sb.toString();
        System.out.println(content.length());

        String s=content;

        char[] data = s.toCharArray();

		start(data);
	}
	public static void start(char[] data) throws Exception{

        File file=new File("src/example-foxykeep/input/sample.json");
        final String fileName = file.getName();

        String columnMetadataText;
        final StringBuilder sb = new StringBuilder();
        BufferedReader br;

        br = new BufferedReader(new FileReader(new File("src/example-foxykeep/res/column_metadata.txt")));
        String line;
        while ((line = br.readLine()) != null) {
            sb.append(line).append("\n");
        }
        columnMetadataText = sb.toString();

//		char[] data;
//		data=s.toCharArray();
//		for(int i=0;i<s.length();i++){
//			if(((char)data[i])!=' '){
//				data[i]=Debug.makeConcolicChar("sym_cnf_char"+i, ""+(int)data[i]);
//			}
//		}

		String str = new String(data);         
        
        
        final JSONObject root = new JSONObject(str);
        final JSONObject jsonDatabase = root.getJSONObject("database");		

        String classPackage, classesPrefix, contentClassesPrefix, dbAuthorityPackage,
        providerFolder;
		int dbVersion;
		boolean hasProviderSubclasses;
		classPackage = jsonDatabase.getString("package");
		classesPrefix = jsonDatabase.getString("classes_prefix");
		contentClassesPrefix = jsonDatabase.optString("content_classes_prefix", "");
		dbAuthorityPackage = jsonDatabase.optString("authority_package", classPackage);
		providerFolder = jsonDatabase.optString("provider_folder",
		        PathUtils.PROVIDER_DEFAULT);
		dbVersion = jsonDatabase.getInt("version");
		hasProviderSubclasses = jsonDatabase.optBoolean("has_subclasses");
		
		ArrayList<TableData> classDataList = TableData.getClassesData(root.getJSONArray(
                "tables"), contentClassesPrefix, dbVersion);

        // Database generation
        DatabaseGenerator.generate(fileName, classPackage, dbVersion, dbAuthorityPackage,
                classesPrefix, classDataList, providerFolder, hasProviderSubclasses);
        
        FileCache.saveFile(PathUtils.getAndroidFullPath(fileName, classPackage,
                providerFolder + "." + PathUtils.UTIL) + "ColumnMetadata.java",
                String.format(columnMetadataText, classPackage,
                        providerFolder + "." + PathUtils.UTIL));
	}
}
