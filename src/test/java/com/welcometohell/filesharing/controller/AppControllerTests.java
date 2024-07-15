//package com.welcometohell.filesharing.controller;
//
//import com.welcometohell.filesharing.repo.UserRepository;
//import com.welcometohell.filesharing.service.FileService;
//import org.junit.jupiter.api.Test;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
//import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
//import org.springframework.boot.test.mock.mockito.MockBean;
//import org.springframework.http.HttpHeaders;
//import org.springframework.http.MediaType;
//import org.springframework.mock.web.MockMultipartFile;
//import org.springframework.security.test.context.support.WithMockUser;
//import org.springframework.test.web.servlet.MockMvc;
//import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
//import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
//
//import java.util.Optional;
//
//import static org.mockito.Mockito.when;
//
//@WebMvcTest(AppController.class)
//@AutoConfigureMockMvc
//public class AppControllerTests {
//
//    @Autowired
//    private MockMvc mockMvc;
//
//    @MockBean
//    private FileService fileService;
//
//    @MockBean
//    private UserRepository userRepository;
//
//    @Test
//    @WithMockUser(username = "testuser", roles = {"USER"})
//    public void testUploadFile() throws Exception {
//        MockMultipartFile file = new MockMultipartFile("file", "test.txt",
//                MediaType.TEXT_PLAIN_VALUE, "Hello, World!".getBytes());
//
//        mockMvc.perform(MockMvcRequestBuilders.multipart("/app/upload")
//                        .file(file)
//                        .param("userId", "1"))
//                .andExpect(MockMvcResultMatchers.status().isOk())
//                .andExpect(MockMvcResultMatchers.content()
//                        .string("File uploaded successfully"));
//    }
//
//
//    @Test
//    @WithMockUser(username = "testuser", roles = {"USER"})
//    public void testDownloadFile() throws Exception {
//        byte[] data = "Hello, World!".getBytes();
//        when(fileService.getFileData(1L)).thenReturn(data);
//        when(fileService.getFileEntity(1L)).thenReturn(Optional.of(new com.welcometohell.filesharing.entity.File()));
//
//        mockMvc.perform(MockMvcRequestBuilders.get("/app/download/1"))
//                .andExpect(MockMvcResultMatchers.status().isOk())
//                .andExpect(MockMvcResultMatchers.content().contentType(MediaType.APPLICATION_OCTET_STREAM))
//                .andExpect(MockMvcResultMatchers.header().string(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=\"null\""));
//    }
//
//    @Test
//    @WithMockUser(username = "testuser", roles = {"USER"})
//    public void testHandleFileShare() throws Exception {
//        mockMvc.perform(MockMvcRequestBuilders.post("/app/share")
//                        .param("fileId", "1")
//                        .param("recipientUsername", "recipient"))
//                .andExpect(MockMvcResultMatchers.status().isOk())
//                .andExpect(MockMvcResultMatchers.content().string("File shared successfully"));
//    }
//
//    @Test
//    @WithMockUser(username = "testuser", roles = {"USER"})
//    public void testDeleteFile() throws Exception {
//        mockMvc.perform(MockMvcRequestBuilders.delete("/app/delete/1"))
//                .andExpect(MockMvcResultMatchers.status().isOk())
//                .andExpect(MockMvcResultMatchers.content().string("File deleted successfully"));
//    }
//}
