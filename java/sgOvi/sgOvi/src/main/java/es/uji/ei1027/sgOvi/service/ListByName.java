package es.uji.ei1027.sgOvi.service;

import java.util.List;
import java.util.Map;

public interface ListByName {
    Map<String, List<String>> personUserList();
    Map<String, List<String>> personPapPatiList();
    Map<String, List<String>> personInstructorList();
}
