package Data;

import java.math.BigDecimal;
import java.math.BigInteger;
import java.security.SecureRandom;

public class ID {
    private Integer FNVal; //firstNameValue
    private Integer LNVal; //lastNameValue
    private BigDecimal Code; //AVLTreeCode for queue

    public ID(String firstName,String lastName){
        this.FNVal = (int) firstName.charAt(0)+(int) firstName.charAt(1);
        this.LNVal = (int) lastName.charAt(0) + (int) lastName.charAt(1);
        this.Code = getFiveDigit();
    }

    public String getID(){
        return String.format("%d-%d-%s",FNVal,LNVal, Code.toString());
    }

    public BigDecimal getDecimalID() {
        try {
            return new BigDecimal(String.format("%d%d%s", FNVal, LNVal, Code.toString()));
        }catch (Exception ex){
            System.out.println(FNVal);
            ex.printStackTrace();
            return null;
        }
    }

    public BigDecimal getCode(){
        return Code;
    }

    private BigDecimal getFiveDigit(){
        SecureRandom random = new SecureRandom();
        int randCode = random.nextInt(10000);
        return new BigDecimal(BigInteger.valueOf(randCode));
    }
}
