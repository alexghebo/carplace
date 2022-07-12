package ca.verticalidigital.carplace.importer;

import ca.verticalidigital.carplace.repository.DealerRepository;
import ca.verticalidigital.carplace.service.VehicleListingService;
import ca.verticalidigital.carplace.service.dto.VehicleListingDTO;
import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.model.S3Object;
import com.fasterxml.jackson.databind.MappingIterator;
import com.fasterxml.jackson.dataformat.csv.CsvMapper;
import com.fasterxml.jackson.dataformat.csv.CsvSchema;
import liquibase.repackaged.com.opencsv.exceptions.CsvValidationException;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;

@EnableScheduling
@Component
public class AWSHelper {
    private final VehicleListingService vehicleListingService;
    private final DealerRepository dealerRepository;
    private final AmazonS3 s3Client;
    @Value("${aws.bucket}")
    private String BUCKET_NAME;

    public AWSHelper(
        VehicleListingService vehicleListingService,
        AmazonS3 s3Client,
        DealerRepository dealerRepository){
        this.vehicleListingService = vehicleListingService;
        this.s3Client = s3Client;
        this.dealerRepository = dealerRepository;
    }

    @Scheduled(fixedRate = 60000*60)
    public void execute() throws IOException, CsvValidationException {
        List<BufferedReader> brs = readS3object(getS3objects(s3Client,getDealersCsv()));
        List<VehicleListingDTO> list = getAllVehicle(brs);
        vehicleListingService.saveAll(list);
    }

    public List<String> getDealersCsv(){
        List<String> dealerCsv = new ArrayList<>();
        dealerRepository.findAllByAutoImportTrue()
            .forEach(
                t->dealerCsv.add(
                    (t.getCsvName().endsWith(".csv") ? t.getCsvName() : t.getCsvName() + ".csv")
                )
            );
        return dealerCsv;
    }

    public List<S3Object> getS3objects(AmazonS3 s3, List<String> dealerCsv){
        List<S3Object> awsObj = new ArrayList<>();
        dealerCsv.forEach(t-> awsObj.add(s3.getObject(BUCKET_NAME,t)));
        return awsObj;
    }

    public List<BufferedReader> readS3object(List<S3Object> s3Object) {
        List<BufferedReader> read = new ArrayList<>();
        s3Object.forEach(t-> read.add(new BufferedReader(new InputStreamReader(t.getObjectContent()))));
        return read;
    }

    private List<VehicleListingDTO> getListFromCSV(BufferedReader br) throws IOException {
        CsvSchema schema = createSchema(br);
        MappingIterator<VehicleListingDTO> list
            = new CsvMapper().readerWithTypedSchemaFor(VehicleListingDTO.class)
            .with(schema)
            .readValues(br);
        return list.readAll();
    }
    public CsvSchema createSchema(BufferedReader br) throws IOException {
        char separator = (br.readLine().contains(";") ? ';' : ',');
        return CsvSchema.builder()
            .addColumn("price", CsvSchema.ColumnType.NUMBER)
            .addColumn("year", CsvSchema.ColumnType.NUMBER)
            .addColumn("mileage", CsvSchema.ColumnType.NUMBER)
            .addColumn("fuel", CsvSchema.ColumnType.STRING)
            .addColumn("status", CsvSchema.ColumnType.STRING)
            .addColumn("internalNumber", CsvSchema.ColumnType.STRING)
            .addColumn("performance", CsvSchema.ColumnType.STRING)
            .addColumn("vat", CsvSchema.ColumnType.BOOLEAN)
            .addColumn("vin", CsvSchema.ColumnType.STRING)
            .addColumn("colour", CsvSchema.ColumnType.STRING)
            .addColumn("ac", CsvSchema.ColumnType.STRING)
            .addColumn("esp", CsvSchema.ColumnType.BOOLEAN)
            .addColumn("abs", CsvSchema.ColumnType.BOOLEAN)
            .addColumn("doors", CsvSchema.ColumnType.NUMBER)
            .addColumn("cubicCapacity", CsvSchema.ColumnType.NUMBER)
            .addColumn("numberOfSeats", CsvSchema.ColumnType.NUMBER)
            .addColumn("emissionClass", CsvSchema.ColumnType.STRING)
            .addColumn("emission", CsvSchema.ColumnType.NUMBER)
            .addColumn("gearbox", CsvSchema.ColumnType.STRING)
            .build()
            .withColumnSeparator(separator);
    }

    public List<VehicleListingDTO> getAllVehicle(List<BufferedReader> brs) throws IOException {
        List<VehicleListingDTO> list = new ArrayList<>();
        for(BufferedReader br : brs){
            list.addAll(getListFromCSV(br));
        }
        return list;
    }
}
