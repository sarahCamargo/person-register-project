package com.crud.person.project.csv;

import com.crud.person.project.model.Pessoa;
import com.crud.person.project.service.PessoaService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.jupiter.api.io.TempDir;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.io.BufferedWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Collections;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class CSVServiceTest {


    @InjectMocks
    private CSVService csvService;

    @Mock
    private PessoaService pessoaService;

    @Test
    void testSaveCSVToFileTest(@TempDir Path tempDir) throws IOException {
        Path outputDir = tempDir.resolve("output");
        Files.createDirectories(outputDir);

        Path filePath = tempDir.resolve("person-report.csv");

        List<Pessoa> pessoasMock = Collections.singletonList(new Pessoa());
        CSVService mockCsvService = mock(CSVService.class);

        doAnswer(invocation -> {
            Files.createDirectories(outputDir);
            try (BufferedWriter writer = Files.newBufferedWriter(filePath)) {
                writer.write(getCsvString());
            }
            return null;
        }).when(mockCsvService).saveCSVToFile(pessoasMock);

        mockCsvService.saveCSVToFile(pessoasMock);

        assertTrue(Files.exists(filePath), "O arquivo CSV não foi criado.");

        String content = Files.readString(filePath);
        assertEquals(content, getCsvString());
    }


    private String getCsvString() {
        return "Id,Nome,Telefone,CPF,CEP,Logradouro,Bairro,Municipio,Estado,Numero,Complemento\n" +
                "1,Sarah,41999999999,123123123,89012520,Rua Teste1,Victor Konder,Blumenau,SC,100,AP 200\n" +
                "2,Lucas,41988888888,321321321,89012520,Rua Teste2,Victor Konder,Blumenau,SC,101,AP 201\n";
    }
}
