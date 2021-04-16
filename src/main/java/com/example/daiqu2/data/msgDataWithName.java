package com.example.daiqu2.data;
import lombok.Data;
import lombok.EqualsAndHashCode;
@EqualsAndHashCode(callSuper = true)
@Data
public class msgDataWithName extends msgData{
    private String fromUserName;
    private String toUserName;
}
