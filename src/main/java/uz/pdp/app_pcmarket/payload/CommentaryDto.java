package uz.pdp.app_pcmarket.payload;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class CommentaryDto {
    private String title;
    private String text;
    private Integer userId;
}
