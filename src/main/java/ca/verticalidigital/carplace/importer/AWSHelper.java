package ca.verticalidigital.carplace.importer;

import ca.verticalidigital.carplace.domain.enumeration.*;
import ca.verticalidigital.carplace.service.VehicleListingService;
import ca.verticalidigital.carplace.service.dto.VehicleListingDTO;
import com.amazonaws.auth.AWSCredentials;
import com.amazonaws.auth.AWSStaticCredentialsProvider;
import com.amazonaws.auth.BasicAWSCredentials;
import com.amazonaws.regions.Regions;
import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.AmazonS3ClientBuilder;
import com.amazonaws.services.s3.model.S3Object;
import liquibase.repackaged.com.opencsv.exceptions.CsvValidationException;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.sql.Timestamp;
import java.time.Instant;
import java.util.ArrayList;
import java.util.List;

@Configuration
@EnableScheduling

public class AWSHelper {
    private VehicleListingService vehicleListingService;

    @Value("${aws.bucket}")
    private String BUCKET_NAME;
    @Value("${aws.object}")
    private String OBJECT_NAME;

    @Value("${aws.accesskey}")
    private String ACCESS_KEY;

    @Value("${aws.secretkey}")
    private String SECRET_KEY;

    public AWSHelper(
        VehicleListingService vehicleListingService
    ){
        this.vehicleListingService = vehicleListingService;
    }

    @Scheduled(fixedRate = 60000*60)
    public void execute() throws IOException, CsvValidationException {
        AmazonS3 s3 = this.getClient();
        S3Object s3Object = this.getObject(s3);
        BufferedReader br = read(s3Object);
        List<VehicleListingDTO> list = getListFromCSV(br);
        vehicleListingService.saveAll(list);
    }

    private VehicleListingDTO getDto(String[] data){
        VehicleListingDTO dto = new VehicleListingDTO();
        dto.setPrice(Integer.parseInt(data[0]));
        dto.setYear(Integer.parseInt(data[1]));
        dto.setMileage(Integer.parseInt(data[2]));
        dto.setFuel(FuelType.valueOf(data[3].toUpperCase()));
        dto.setStatus(ListingStatus.valueOf(data[4].toUpperCase()));
        dto.setInternalNumber(data[5]);
        dto.setPerformance(Integer.parseInt(data[6]));
        dto.setVat(valueOfNumber(data[7]));
        dto.setVin(data[8]);
        dto.setColour(data[9]);
        dto.setAc(Ac.valueOf(data[10].toUpperCase()));
        dto.setEsp(valueOfNumber(data[11]));
        dto.setAbs(valueOfNumber(data[12]));
        dto.setDoors(Integer.parseInt(data[13]));
        dto.setCubicCapacity(Integer.parseInt(data[14]));
        dto.setNumberOfSeats(Integer.parseInt(data[15]));
        dto.setEmissionClass(EmissionClass.valueOf(data[16].toUpperCase()));
        dto.setEmission(Integer.parseInt(data[17]));
        dto.setGearbox(Gearbox.valueOf(data[18].toUpperCase()));
        dto.setRegDate(getInstant());
        return dto;
    }

    private Boolean valueOfNumber(String number){
        int nr = Integer.parseInt(number);
        return nr != 0;
    }

    private Instant getInstant(){
        Timestamp timestamp = new Timestamp(System.currentTimeMillis());
        return timestamp.toInstant();
    }

    public AmazonS3 getClient(){
        AWSCredentials credentials = new BasicAWSCredentials(ACCESS_KEY, SECRET_KEY);
        return AmazonS3ClientBuilder.standard()
            .withCredentials(new AWSStaticCredentialsProvider(credentials))
            .withRegion(Regions.EU_CENTRAL_1)
            .build();
    }

    public S3Object getObject(AmazonS3 s3){
        return s3.getObject(BUCKET_NAME,OBJECT_NAME);
    }

    private List<VehicleListingDTO> getListFromCSV(BufferedReader br) throws IOException {
        String line;
        List<VehicleListingDTO> list = new ArrayList<>();
        while ((line=br.readLine())!=null){
            String[] data = line.split(";");
            list.add(this.getDto(data));
        }
        return list;
    }

    public BufferedReader read(S3Object s3Object) throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(s3Object.getObjectContent()));
        br.readLine();
        return br;
    }
}
