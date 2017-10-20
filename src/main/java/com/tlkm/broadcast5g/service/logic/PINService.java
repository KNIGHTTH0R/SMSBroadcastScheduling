package com.tlkm.broadcast5g.service.logic;

import com.tlkm.broadcast5g.service.dao.SMSDao;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.Random;

@Service
public class PINService {

    @Autowired
    SMSDao SMSDao;

    public String generatePIN(String msisdn){
        String pinTemp = "";

        System.out.println("lenght : "+msisdn.length());

        pinTemp = "G" + msisdn.substring(msisdn.length() - 4, msisdn.length()-1);

        pinTemp = generateFromDB(pinTemp,1);

        return pinTemp;
    }

    public String generateFromDB(String pin, int indexGen){
        StringBuilder sb = new StringBuilder(pin);

        if(isPINExist(pin)){

            if(indexGen>pin.length()-1){

                pin = pin + generateRandomString(indexGen);
            }else{
                sb.setCharAt(indexGen, generateRandomString(1).charAt(0));

                pin = sb.toString();
            }

            return generateFromDB(pin, indexGen+1);
        }else{
            return pin;
        }
    }

    public boolean isPINExist(String pin){
      //  return DBAccess.getInstance().checkPIN(pin);
        return SMSDao.isPinExist(pin);
    }

    private static Random random = new Random((new Date()).getTime());

    public static String generateRandomString(int length) {
        char[] values = {'a','b','c','d','e','f','g','h','i','j',
                'k','l','m','n','o','p','q','r','s','t',
                'u','v','w','x','y','z','0','1','2','3',
                '4','5','6','7','8','9'};

        String out = "";

        for (int i=0;i<length;i++) {
            int idx=random.nextInt(values.length);
            out += values[idx];
        }
        return out;
    }

}
