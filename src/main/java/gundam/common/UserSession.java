package gundam.common;

import java.io.Serializable;

public class UserSession implements Serializable {
    private OperatorInfo operatorInfo;

    public OperatorInfo getOperatorInfo() {
        return operatorInfo;
    }

    public void setOperatorInfo(OperatorInfo operatorInfo) {
        this.operatorInfo = operatorInfo;
    }

    @Override
    public String toString() {
        return "UserSession{" +
                "operatorInfo=" + operatorInfo +
                '}';
    }
}
