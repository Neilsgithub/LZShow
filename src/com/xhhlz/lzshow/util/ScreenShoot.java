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
	 * @param width 屏幕宽度
	 * @param height 屏幕高度
	 * @param mywindow 要获取的窗口
	 * @param path 要存储的目录路径,如"/LZShow/practice"
	 */
	
	public ScreenShoot(int width, int height, Window mywindow, String path)
	{
		this.screenHeight = height;
		this.screenWidth = width;
		this.window = mywindow;
		this.fold = path;	
		
		//获得系统时间，修改系统时间的显示方式用于命名文件
		Date now = new Date(); 
		SimpleDateFormat dateFormat = new SimpleDateFormat("yyMMdd_HHmmss");
		String temp = dateFormat.format(now);
		saveName = temp+".jpg";
	}
	
	/**
	 * 获取屏幕图片
	 * @return 0 正常获得图片； 1 获取截屏失败；2 SD卡不存在
	 */
	public int getCurrentImage()
	{
		// 1.构建Bitmap
		int w = screenWidth;
		int h = screenHeight;

		Bitmap myBmp = Bitmap.createBitmap(w, h, Config.ARGB_8888);

		// 2.获取屏幕
		View decorview = window.getDecorView();
		decorview.setDrawingCacheEnabled(true);
		myBmp = decorview.getDrawingCache();
		
		//获得标题栏的高度
		Rect frame = new Rect();  
        window.getDecorView().getWindowVisibleDisplayFrame(frame);  
        int statusBarHeight = frame.top;  
  
        // 去掉标题栏 //Bitmap b = Bitmap.createBitmap(b1, 0, 25, 320, 455);  
        Bitmap Bmp = Bitmap.createBitmap(myBmp, 0, statusBarHeight, w, h 
                - statusBarHeight); 
		
		savePath = getSDCardPath() + fold;
		if(getSDCardPath() == null)
			return 2;

		// 3.保存Bitmap
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
	 * 获取SDCard的目录路径功能
	 * 
	 * @return 如果SD存在，SD卡的目录路径；如果不存在，返回null;
	 */
	private String getSDCardPath() {
		File sdcardDir = null;
		// 判断SDCard是否存在
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
	 * 获得屏幕截屏的路径
	 * @return 屏幕截屏得到的路径
	 */
	public String getFilePath()
	{
		String path = savePath + '/' + saveName;
		return path;
	}
}
