package com.green.car.service;

import org.springframework.stereotype.Service;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.UUID;

@Service
public class FileService {
    //파일 업로드 하기 (경로, 원본파일 이름, 바이트 배열타입의 파일을 매개변수로 받음)
    public String uploadFile(String uploadPath, String originalFile, byte[] fileData) throws IOException {
        //dog1.jpg---> uuid 중복되지 않도록 새로운 파일 이름 생성
        //dog2.png, dog3.gif ---> 확장자를 붙여줘야 함
        UUID uuid = UUID.randomUUID();
        //originalFile에서 확장자만 떼어오기
        String extension = originalFile.substring(originalFile.lastIndexOf("."));
        //새로운 uuid 파일명+확장자
        String saveFileName = uuid.toString()+extension;
        //경로와 파일명 더해줌 fileUploadUrl = C://car/image/uuid.jpg
        String fileUploadUrl = uploadPath+"/"+ saveFileName;
        //FileOutputStream : 바이트 단위의 출력을 내보내는 클래스
        FileOutputStream fos = new FileOutputStream(fileUploadUrl);
        //파일데이터를 파일 아웃풋 스트림에 쓰기
        fos.write(fileData);
        fos.close();
        //실제 저장된 파일 이름 리턴 ---> 화면에 뿌려줄 때 img src 속성으로 뿌려줄 수 있음
        return saveFileName;
    }

    //파일 삭제하기
    public void deleteFile(String filePath){
        //저장된 경로를 이용하여 파일 객체 생성
        File deleteFile = new File(filePath);
        //해당 파일이 존재하면 삭제
        if(deleteFile.exists()){
            deleteFile.delete();
            System.out.println("파일을 삭제했습니다.");
        }else{
            System.out.println("파일이 존재하지 않습니다.");
        }
    }
}
