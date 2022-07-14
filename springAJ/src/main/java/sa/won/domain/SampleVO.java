package sa.won.domain;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data

//모든 속성을 사용하는 생성자를 위함
@AllArgsConstructor

//비어있는 생성자를 만들기 위함
@NoArgsConstructor
public class SampleVO {

	
	private Integer mno;
	private String firstName;
	private String lastName;
}
