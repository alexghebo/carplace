package ca.verticalidigital.carplace.importer;

import ca.verticalidigital.carplace.service.VehicleListingService;
import ca.verticalidigital.carplace.service.dto.VehicleListingDTO;
import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.event.S3EventNotification;
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
import java.util.List;

@EnableScheduling
@Component
public class AWSHelper {
    private final VehicleListingService vehicleListingService;

    private final AmazonS3 s3Client;

    @Value("${aws.bucket}")
    private String BUCKET_NAME;
    @Value("${aws.object}")
    private String OBJECT_NAME;


    public AWSHelper(
        VehicleListingService vehicleListingService,
        AmazonS3 s3Client){
        this.vehicleListingService = vehicleListingService;
        this.s3Client = s3Client;
    }

    @Scheduled(fixedRate = 60000*60)
    public void execute() throws IOException, CsvValidationException {
        S3Object s3Object = this.getObject(s3Client);
        BufferedReader br = read(s3Object);
        CsvSchema schema = createSchema();
        List<VehicleListingDTO> list = getListFromCSV(br,schema);
        vehicleListingService.saveAll(list);
    }


    public S3Object getObject(AmazonS3 s3){
        return s3.getObject(BUCKET_NAME,OBJECT_NAME);
    }

    public BufferedReader read(S3Object s3Object) throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(s3Object.getObjectContent()));
        br.readLine();
        return br;
    }
    private CsvSchema createSchema() {
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
            .withColumnSeparator(';');
    }
    private List<VehicleListingDTO> getListFromCSV(BufferedReader br, CsvSchema schema) throws IOException {
        MappingIterator<VehicleListingDTO> list
            = new CsvMapper().readerWithTypedSchemaFor(VehicleListingDTO.class)
            .with(schema)
            .readValues(br);
        return list.readAll();
    }

}
