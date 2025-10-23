package uoc.edu.citaprevia.dto.generic;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Setter @Getter @NoArgsConstructor @ToString
public class ErrorsDto {

	private List<ErrorDto> errors;

	public ErrorsDto(List<ErrorDto> errors) {
		this.errors = errors;
	}

	public boolean hasErrors() {
		return errors != null && !errors.isEmpty();
	}

	public boolean hasErrorsStrict() {
		if (errors == null || (errors != null && errors.isEmpty())) {
			return false;
		}
		for (ErrorDto error : errors) {
			if (!"A".equals(error.getTip())) { return true; }
		}
		return false;
	}

	public boolean hasOnlyWarnings() {
		if (errors == null || (errors != null && errors.isEmpty())) { return false; }
		for (ErrorDto error : errors) {
			if ("E".equals(error.getTip())) { return false; }
		}
		return true;
	}

	public List<ErrorDto> removeWarnings() {
		if (errors == null || (errors != null && errors.isEmpty())) { return errors; }

		for (Iterator<ErrorDto> iter = errors.listIterator(); iter.hasNext();) {
			ErrorDto a = iter.next();
			if (!"E".equals(a.getTip())) { iter.remove(); }
		}
		return errors;
	}

	public void addError(ErrorDto error) {
		if (errors == null) { errors = new ArrayList<>(); }
		errors.add(error);
	}
}