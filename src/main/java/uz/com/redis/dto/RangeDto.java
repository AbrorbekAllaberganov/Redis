package uz.com.redis.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class RangeDto {
    private int from;
    private int to;

    public int getFrom(){
        return from;
    }

}
