package sa.won.domain;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data

//��� �Ӽ��� ����ϴ� �����ڸ� ����
@AllArgsConstructor

//����ִ� �����ڸ� ����� ����
@NoArgsConstructor
public class SampleVO {

	
	private Integer mno;
	private String firstName;
	private String lastName;
}
