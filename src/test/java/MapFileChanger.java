import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;
import java.lang.annotation.Annotation;
import java.lang.reflect.Field;
import java.net.URL;
import java.nio.charset.Charset;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Set;
import java.util.TreeSet;

import javax.persistence.Column;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.persistence.Temporal;

import org.apache.commons.lang.ArrayUtils;
import org.apache.commons.lang.WordUtils;

import com.sun.codemodel.JClassAlreadyExistsException;
import com.sun.codemodel.JCodeModel;
import com.sun.codemodel.JDefinedClass;
import com.sun.codemodel.JExpr;
import com.sun.codemodel.JMod;
import com.sun.codemodel.JPackage;
import com.sun.codemodel.writer.SingleStreamCodeWriter;

public class MapFileChanger {
	private static final String PATH = "C:/JADK/safekeyws/Safekey/src/main/java/com/bankasya/safekey/datasource/entity";
	private static final String MAPPING_FILE_EXTENSION = ".java";
	private static final String paketName = "com.gom.safekey.datasource.entity";

	public static void main(String[] args) throws IOException {

		File dir = new File(PATH);

		HashMap<String, String> hashMap = getColumnNameAsStatic();

		if (dir.isDirectory()) {
			File[] files = dir.listFiles();
			for (File file : files) {
				if (file.isFile()) {
					String className = file.getName().replace(".java", "");
					if (hashMap.containsKey(className)) {
						String fileContent = getFileContent(file);
						String classContent = hashMap.get(className);
						/*
						 * String curly = "\\{"; // int pos =
						 * fileContent.indexOf("{"); String fileContent1 =
						 * fileContent.replaceFirst(curly,classContent);
						 * writeFileContent(file, fileContent1);
						 */

						String strDontRemoveBegin = "/* tableNames dont remove begin */";
						String strDontRemoveEnd = "/* tableNames end */";
						int indexDontRemoveBegin = fileContent.indexOf(strDontRemoveBegin);
						int indexDontRemoveEnd = fileContent.indexOf(strDontRemoveEnd);

						if (indexDontRemoveBegin > 0 && indexDontRemoveEnd > 0) {

							String strBefore = fileContent.substring(0, indexDontRemoveBegin);
							String strAfter = fileContent.substring(indexDontRemoveEnd);

							strBefore = strBefore.substring(0, strBefore.lastIndexOf(10));
							strAfter = strAfter.substring(strAfter.indexOf(10));

							String fileContent1 = strBefore + "\n	" + strDontRemoveBegin + "\n	"
									+ classContent.trim() + "\n	" + strDontRemoveEnd + "\n\n	" + strAfter.trim();
							writeFileContent(file, fileContent1);
						} else {
							System.err.println(className + " column tag not found");
						}
					}
				}
			}

		}

		System.out.println("ENDED!!!!!!!!!!!!!!!");
	}

	public static void generateStaticColumnName() {

	}

	private static HashMap<String, String> getColumnNameAsStatic() {
		HashMap<String, String> hashMap = new HashMap<String, String>();
		Class[] classes = {};
		try {
			classes = getClasses(paketName);
		} catch (ClassNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		HashMap<String, List<String>> hashMapColumnName = new HashMap<String, List<String>>();

		for (Class class1 : classes) {
			Field[] fields = class1.getDeclaredFields();
			Set<String> columnNameList = new TreeSet<String>();
			for (Field field : fields) {
				columnNameList.addAll(printAnnotation(class1, field));
			}
			hashMapColumnName.put(class1.getSimpleName(), new ArrayList<String>(columnNameList));
		}

		Set<String> classNames = hashMapColumnName.keySet();
		for (String className1 : classNames) {
			JCodeModel cm = new JCodeModel();

			JPackage jPackage = cm._package(paketName);

			String className = "TableColumns";
			try {
				JDefinedClass jDefinedClass = jPackage._class(JMod.PUBLIC, className);
				List<String> columnNameList = hashMapColumnName.get(className1);
				if (columnNameList.isEmpty()) {
					continue;
				}
				for (String columnName : columnNameList) {
					jDefinedClass.field(JMod.PUBLIC + JMod.FINAL + JMod.STATIC, String.class, "PROP_" + columnName,
							JExpr.lit(capitalizeColumnName(columnName)));
				}

			} catch (JClassAlreadyExistsException e) {
				e.printStackTrace();
			}
			ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
			SingleStreamCodeWriter streamCodeWriter = new SingleStreamCodeWriter(outputStream);

			try {
				cm.build(streamCodeWriter);
				String fileContent = outputStream.toString("UTF-8");
				int firt = fileContent.indexOf("{");
				int last = fileContent.indexOf("}");
				hashMap.put(className1, "	" + fileContent.substring(firt + 1, last));
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}

		return hashMap;

	}

	public static Set<String> printAnnotation(Class class1, Field field) {
		// List<String> columnNameList = new ArrayList<String>();
		Set<String> columnNameList = new TreeSet<String>();
		for (Annotation annotation : field.getAnnotations()) {
			if (annotation instanceof Id) {
				Id id = (Id) annotation;
				columnNameList.add(unCapitalizeColumnName(class1.getSimpleName()));
				columnNameList.add(unCapitalizeColumnName(field.getName()));
				// break;
			}
			if (annotation instanceof Column) {
				Column column = (Column) annotation;
				/*
				 * / if (!column.name().isEmpty()) {
				 * columnNameList.add(column.name()); } else {
				 * columnNameList.add(unCapitalizeColumnName(field.getName()));
				 * }
				 */
				columnNameList.add(unCapitalizeColumnName(field.getName()));
				break;
			}
			if (annotation instanceof ManyToOne || annotation instanceof OneToMany || annotation instanceof OneToOne
					|| annotation instanceof JoinColumn || annotation instanceof Temporal) {
				columnNameList.add(unCapitalizeColumnName(field.getName()));
			}
		}
		return columnNameList;
	}

	private static Class[] getClasses(String packageName) throws ClassNotFoundException, IOException {
		ClassLoader classLoader = Thread.currentThread().getContextClassLoader();
		assert classLoader != null;
		String path = packageName.replace('.', '/');
		Enumeration<URL> resources = classLoader.getResources(path);
		List<File> dirs = new ArrayList<File>();
		while (resources.hasMoreElements()) {
			URL resource = resources.nextElement();
			dirs.add(new File(resource.getFile()));
		}
		ArrayList<Class> classes = new ArrayList<Class>();
		for (File directory : dirs) {
			classes.addAll(findClasses(directory, packageName));
		}
		return classes.toArray(new Class[classes.size()]);
	}

	private static List<Class> findClasses(File directory, String packageName) throws ClassNotFoundException {
		List<Class> classes = new ArrayList<Class>();
		if (!directory.exists()) {
			return classes;
		}
		File[] files = directory.listFiles();
		for (File file : files) {
			if (file.isDirectory()) {
				// assert !file.getName().contains(".");
				// classes.addAll(findClasses(file, packageName + "." +
				// file.getName()));
			} else if (file.getName().endsWith(".class")) {
				classes.add(
						Class.forName(packageName + '.' + file.getName().substring(0, file.getName().length() - 6)));
			}
		}
		return classes;
	}

	private static String getFileContent(File file) throws IOException {
		StringBuilder stringBuilder = new StringBuilder();
		Path path = Paths.get(file.getParent(), file.getName());
		List<String> strings = Files.readAllLines(path, Charset.forName("UTF-8"));
		for (String string : strings) {
			stringBuilder.append(string);
			stringBuilder.append("\n");
		}
		return stringBuilder.toString();

	}

	private static void writeFileContent(File file, String fileContent) throws IOException {
		Path path = Paths.get(file.getParent(), file.getName());
		Files.write(path, fileContent.getBytes());

	}

	/**
	 * @param columnName
	 * @return vpTransactionTime --> VP_TRANSACTION_TIME
	 */
	public static String unCapitalizeColumnName(String columnName) {
		char[] chars = columnName.toCharArray();
		List<Object> charList = new ArrayList<Object>();
		for (int i = 0; i < chars.length; i++) {
			char ch = chars[i];
			if (i > 0) {
				if (!Character.isLowerCase(ch)) {
					charList.add('_');
				}
			}
			charList.add(ch);
		}
		char[] character = ArrayUtils.toPrimitive(charList.toArray(new Character[charList.size()]));

		return new String(character).toUpperCase(Locale.ENGLISH);
	}

	/**
	 * @param columnName
	 * @return String VP_TRANSACTION_TIME -->vpTransactionTime
	 * @throws IOException
	 */
	public static String capitalizeColumnName(String columnName) {
		char[] chr = { '_' };

		columnName = WordUtils.capitalizeFully(columnName, chr);

		char[] chars = columnName.toCharArray();
		chars[0] = Character.toLowerCase(chars[0]);
		columnName = new String(chars);
		return columnName.replaceAll("_", "");
	}

}
