package gundam.pojo;

public class BsStaticData {
    private String  codeType;
    private String codeValue;
    private String codeName;
    private String codeDesc;
    private String codeTypeAlias;
    private String state;
    private String externCodeType;

    public String getCodeType() {
        return codeType;
    }

    public void setCodeType(String codeType) {
        this.codeType = codeType;
    }

    public String getCodeValue() {
        return codeValue;
    }

    public void setCodeValue(String codeValue) {
        this.codeValue = codeValue;
    }

    public String getCodeName() {
        return codeName;
    }

    public void setCodeName(String codeName) {
        this.codeName = codeName;
    }

    public String getCodeDesc() {
        return codeDesc;
    }

    public void setCodeDesc(String codeDesc) {
        this.codeDesc = codeDesc;
    }

    public String getCodeTypeAlias() {
        return codeTypeAlias;
    }

    public void setCodeTypeAlias(String codeTypeAlias) {
        this.codeTypeAlias = codeTypeAlias;
    }

    public String getState() {
        return state;
    }

    public void setState(String state) {
        this.state = state;
    }

    public String getExternCodeType() {
        return externCodeType;
    }

    public void setExternCodeType(String externCodeType) {
        this.externCodeType = externCodeType;
    }

    @Override
    public String toString() {
        return "BsStaticData{" +
                "codeType='" + codeType + '\'' +
                ", codeValue='" + codeValue + '\'' +
                ", codeName='" + codeName + '\'' +
                ", codeDesc='" + codeDesc + '\'' +
                ", codeTypeAlias='" + codeTypeAlias + '\'' +
                ", state='" + state + '\'' +
                ", externCodeType='" + externCodeType + '\'' +
                '}';
    }
}
