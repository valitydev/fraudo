package dev.vality.fraudo.test.model;

import dev.vality.fraudo.model.BaseModel;
import lombok.Data;

@Data
public class P2PModel extends BaseModel {

    private String bin;
    private String pan;
    private String country;
    private String cardTokenFrom;
    private String cardTokenTo;
    private String identityId;

}
