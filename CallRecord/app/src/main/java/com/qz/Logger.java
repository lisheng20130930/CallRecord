/**
 * @ClassName Logger
 * @Description Logger
 * @author Listen.Li
 * @version V1.0
 * @Date 20160325 Listen.Li v1.0      Create
 */
package com.qz;

import android.os.Environment;

import java.io.File;
import java.io.FileOutputStream;

public class Logger {
	public static void log(String txt) {
		String _pszFile = Environment.getExternalStorageDirectory().getPath() + "/Demo_LOG.txt";
		try {
			android.util.Log.e("LOG", txt);
			FileOutputStream fout = new FileOutputStream(_pszFile, true);
			fout.write((txt+"\r\n").getBytes());
			fout.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}
