package com.xhhlz.lzshow.util;

import java.io.File;
import java.io.FileOutputStream;
import java.text.SimpleDateFormat;
import java.util.Date;

import android.graphics.Bitmap;
import android.graphics.Bitmap.Config;
import android.graphics.Rect;
import android.os.Environment;
import android.view.View;
import android.view.Window;

public class ScreenShoot {
	private int screenWidth;
	private int screenHeight;
	private Window window;
	private String fold;
	private String savePath;
	private String saveName;
	
	/**
	 * 
	 * @param width ��Ļ���
	 * @param height ��Ļ�߶�
	 * @param mywindow Ҫ��ȡ�Ĵ���
	 * @param path Ҫ�洢��Ŀ¼·��,��"/LZShow/practice"
	 */
	
	public ScreenShoot(int width, int height, Window mywindow, String path)
	{
		this.screenHeight = height;
		this.screenWidth = width;
		this.window = mywindow;
		this.fold = path;	
		
		//���ϵͳʱ�䣬�޸�ϵͳʱ�����ʾ��ʽ���������ļ�
		Date now = new Date(); 
		SimpleDateFormat dateFormat = new SimpleDateFormat("yyMMdd_HHmmss");
		String temp = dateFormat.format(now);
		saveName = temp+".jpg";
	}
	
	/**
	 * ��ȡ��ĻͼƬ
	 * @return 0 �������ͼƬ�� 1 ��ȡ����ʧ�ܣ�2 SD��������
	 */
	public int getCurrentImage()
	{
		// 1.����Bitmap
		int w = screenWidth;
		int h = screenHeight;

		Bitmap myBmp = Bitmap.createBitmap(w, h, Config.ARGB_8888);

		// 2.��ȡ��Ļ
		View decorview = window.getDecorView();
		decorview.setDrawingCacheEnabled(true);
		myBmp = decorview.getDrawingCache();
		
		//��ñ������ĸ߶�
		Rect frame = new Rect();  
        window.getDecorView().getWindowVisibleDisplayFrame(frame);  
        int statusBarHeight = frame.top;  
  
        // ȥ�������� //Bitmap b = Bitmap.createBitmap(b1, 0, 25, 320, 455);  
        Bitmap Bmp = Bitmap.createBitmap(myBmp, 0, statusBarHeight, w, h 
                - statusBarHeight); 
		
		savePath = getSDCardPath() + fold;
		if(getSDCardPath() == null)
			return 2;

		// 3.����Bitmap
		try {
			File path = new File(savePath);
			if (!path.exists()) {
				path.mkdirs();
			}
		
			File file = new File(path, saveName);
			if (!file.exists()) {
				file.createNewFile();
			}
			
			FileOutputStream fos = null;
			fos = new FileOutputStream(file);
			if (null != fos) {
				Bmp.compress(Bitmap.CompressFormat.PNG, 90, fos);
				fos.flush();
				fos.close();
			}

		} catch (Exception e) {
			e.printStackTrace();
			return 1;
		}
		return 0;
	}

	/**
	 * ��ȡSDCard��Ŀ¼·������
	 * 
	 * @return ���SD���ڣ�SD����Ŀ¼·������������ڣ�����null;
	 */
	private String getSDCardPath() {
		File sdcardDir = null;
		// �ж�SDCard�Ƿ����
		boolean sdcardExist = Environment.getExternalStorageState().equals(
				android.os.Environment.MEDIA_MOUNTED);
		if (sdcardExist) {
			sdcardDir = Environment.getExternalStorageDirectory();
			return sdcardDir.toString();
		}
		else
		{
			return null;
		}
	}
	
	/**
	 * �����Ļ������·��
	 * @return ��Ļ�����õ���·��
	 */
	public String getFilePath()
	{
		String path = savePath + '/' + saveName;
		return path;
	}
}
