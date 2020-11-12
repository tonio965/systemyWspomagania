package interfaces;

import java.util.List;

import models.DataColumn;

public interface DataSender {
	
	void send(List<DataColumn> data);

}
