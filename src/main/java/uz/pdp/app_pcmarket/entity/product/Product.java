package uz.pdp.app_pcmarket.entity.product;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import uz.pdp.app_pcmarket.entity.attachment.Attachment;
import uz.pdp.app_pcmarket.entity.category.Category;
import uz.pdp.app_pcmarket.entity.measurement.Measurement;
import uz.pdp.app_pcmarket.entity.template.AbsEntity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.ManyToOne;
import javax.persistence.OneToOne;

@EqualsAndHashCode(callSuper = true)
@Data
@AllArgsConstructor
@NoArgsConstructor

@Entity
public class Product extends AbsEntity {

    @ManyToOne
    private Category category;

    @OneToOne
    private Attachment photo;

    @ManyToOne
    private Measurement measurement;

    @Column(nullable = false, unique = true)
    private String code;

    @Column(nullable = false)
    private Double price;

    private Integer warrantyYear;

    private String others;
}
