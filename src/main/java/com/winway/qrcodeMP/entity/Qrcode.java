package com.winway.qrcodeMP.entity;

import java.io.Serializable;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

/**
 * <p>
 * 
 * </p>
 *
 * @author jobob
 * @since 2021-01-14
 */
@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
public class Qrcode implements Serializable {

    private static final long serialVersionUID = 1L;

    private String name;

    private Double lng;

    private Double lat;


}
