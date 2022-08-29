package be.intecbrussel.schoolManagementpackage;

import be.intecbrussel.schoolManagementpackage.controller.StudentRestController;
import be.intecbrussel.schoolManagementpackage.model.Student;
import be.intecbrussel.schoolManagementpackage.service.interfaces.StudentService;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.ObjectWriter;
import com.fasterxml.jackson.databind.SerializationFeature;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.http.converter.xml.Jaxb2RootElementHttpMessageConverter;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;


@RunWith(SpringRunner.class)
@WebMvcTest(StudentRestController.class)
public class StudentRestControllerIT {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    StudentService studentService;

    @InjectMocks
    private StudentRestController studentRestController;
    @MockBean
    Student student;

    @Before
    public void setup() {

        MockitoAnnotations.initMocks(this);
        mockMvc = MockMvcBuilders
                .standaloneSetup(studentRestController)
                .setMessageConverters(
                        new MappingJackson2HttpMessageConverter(),
                        new Jaxb2RootElementHttpMessageConverter()).build();

    }


    @Test
    public void testGetAll() throws Exception {
        mockMvc.perform(get("/student/all"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8));


    }

    @Test
    public void testGetStudentById() throws Exception {
        Student student = new Student();
        student.setId(1);
        student.setName("zara");
        student.setAddress("abcde");
        student.setEmailAddress("email");
        when(studentService.getStudentById(anyLong())).thenReturn(student);
        mockMvc.perform(
                        MockMvcRequestBuilders.get("/student/1")
                                .accept(MediaType.APPLICATION_JSON))
                .andExpect(content().json(
                        "{" +
                                "id=1, name='zara', emailAddress='email', phoneNumber=0, address='abcde'" +
                                "}"))
                .andExpect(MockMvcResultMatchers.status().isOk());

    }

    @Test
    public void testAddNewStudent() throws Exception {
        Student student = new Student();
        student.setId(1);
        student.setName("zara");
        student.setAddress("abcde");
        student.setEmailAddress("email");
        ObjectMapper mapper = new ObjectMapper();
        mapper.configure(SerializationFeature.WRAP_ROOT_VALUE, false);
        ObjectWriter ow = mapper.writer().withDefaultPrettyPrinter();
        String requestJson=ow.writeValueAsString(student);


        mockMvc.perform(post("/student/add")
                        .contentType(MediaType.
                                APPLICATION_JSON_UTF8)
                        .content(requestJson))
                .andExpect(status().isCreated());



    }
}


