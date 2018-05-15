package net.board.action;

import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import java.io.*;
public class BoardFileDownAction implements Action {

	@Override
	public ActionForward execute(HttpServletRequest request, HttpServletResponse response) throws Exception {
		request.setCharacterEncoding("UTF-8");
		
		
		
		String filename = request.getParameter("filename");		//��Ŀ�±׷� �����̸� �ް�
		System.out.println(filename);
		
		
		String savePath = "boardupload";
		
		//������ ���� ȯ�� ������ ��� �ִ� ��ü�� �����մϴ�.
		//(appliaction ���� ��ü�� �����մϴ�.
		ServletContext context = request.getServletContext(); 
    	String sDownloadPath = context.getRealPath(savePath);
    	
    	//�� �ι����� �� �������� ��Ÿ���� ������ �����ϴ�.
    	//String sDownloadPath = application.getRealPath(savePath);
		
    	
    	
		//String sFIlePath = sDownloadPath + "/" + filename
    	String sFilePath = sDownloadPath + "/" + filename;				//�ڽ��������� ���������� ��ġ
    	System.out.println(filename);
    	
    	byte b[] = new byte[4096];
    	
    	
    	String sMimeType = context.getMimeType(sFilePath);			//�������� �ѷ��ٶ� ��Ÿ���� ��� ex) html, png, jpg �̷��� ��Ÿ���¾�
    	System.out.println("sMimeType >>>" + sMimeType);

    	if(sMimeType == null)
    		sMimeType = "application/octet-stream";					//����Ÿ���� ���¾ֵ��� �̷��� ��Ÿ�����
    	response.setContentType(sMimeType);
    	
    	String sEncoding =
				new String(filename.getBytes("UTF-8"),"ISO-8859-1");			//iso : ������ ���� �ִ� ĳ����
			System.out.println(sEncoding);
			
			response.setHeader("Content-Disposition","attachment; filename = " + sEncoding);		//response �� ��û�� ���� ������ �������ִ� ��ü�̴�
			
		
			try(
			
				//������������ ��� ��Ʈ�� �����մϴ�.
					BufferedOutputStream out2 = 
					new BufferedOutputStream(response.getOutputStream());
				//sFilePath �� ������ ���Ͽ� ���� �Է� ��Ʈ���� �����մϴ�.
					BufferedInputStream in =
					new BufferedInputStream(new FileInputStream(sFilePath));)
			{
				int numRead;
				//read(b,0, b.length) : ����Ʈ �迭 b�� 0�� ���� b.length
				//ũ�� ��ŭ �о�ɴϴ�.
					while((numRead = in.read(b,0,b.length)) != -1) {//������ ������
						//����Ʈ �迭 b�� 0 ������ numRead ũ�⸸ŭ ������ ���
						out2.write(b,0,numRead);
					}
			}
			
    	
    	
    	
    	return null;
	}

}
