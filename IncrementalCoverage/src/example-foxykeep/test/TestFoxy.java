package test;

import com.foxykeep.cpcodegenerator.model.TableData;
import com.foxykeep.cpcodegenerator.util.PathUtils;
import coverage.SubjectExecutor;
import org.json.JSONObject;

import java.io.IOException;
import java.util.ArrayList;

public class TestFoxy extends SubjectExecutor {
    public static void main(String[] args) throws IOException, ClassNotFoundException {
        packagePrefix = "com.foxykeep.cpcodegenerator";
        inputFileName = args[0];
        new TestFoxy().wrapExecute();
    }
    @Override
    public void execute(String input) throws Throwable {

        final JSONObject root = new JSONObject(input);
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

    }
}
