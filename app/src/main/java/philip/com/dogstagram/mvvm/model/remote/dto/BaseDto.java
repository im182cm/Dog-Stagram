package philip.com.dogstagram.mvvm.model.remote.dto;

import android.support.annotation.VisibleForTesting;
import android.text.TextUtils;

public class BaseDto<T> {
    private final String SUCCESS = "success";

    private final String status;
    private final T message;

    @Override
    public String toString() {
        return "BaseDto{" +
                "status='" + status + '\'' +
                ", message=" + message +
                '}';
    }

    public String getStatus() {
        return status;
    }

    public T getMessage() {
        return message;
    }

    @VisibleForTesting
    public BaseDto(String status, T message) {
        this.status = status;
        this.message = message;
    }

    public boolean isSuccess() {
        if (TextUtils.isEmpty(status)) {
            return false;
        }

        return SUCCESS.equals(status);
    }
}
