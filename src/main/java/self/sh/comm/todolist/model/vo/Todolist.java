package self.sh.comm.todolist.model.vo;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@NoArgsConstructor
@ToString
public class Todolist {
	private int todolistNo;
	private int memberNo;
	private String memberId;
	private String todolistContent;
	private String todolistDate;
	private int todolist_Type;
	private String todolist_fl;
}
