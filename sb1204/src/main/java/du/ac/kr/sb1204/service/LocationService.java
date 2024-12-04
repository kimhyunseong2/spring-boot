package du.ac.kr.sb1204.service;

import com.google.maps.GeoApiContext;
import com.google.maps.PlacesApi;
import com.google.maps.model.*;
import du.ac.kr.sb1204.entity.Location;
import du.ac.kr.sb1204.repository.LocationRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
@RequiredArgsConstructor
public class LocationService {
    private final LocationRepository locationRepository;
    
    @Value("${google.maps.api.key}")
    private String apiKey;
    
    public Location analyzeLocation(String name, double latitude, double longitude) {
        Location location = new Location();
        location.setName(name);
        location.setLatitude(latitude);
        location.setLongitude(longitude);
        
        try {
            GeoApiContext context = new GeoApiContext.Builder()
                .apiKey(apiKey)
                .build();

            LatLng locationLatLng = new LatLng(latitude, longitude);
            int totalCount = 0;

            // 한 번의 API 호출로 모든 타입 검색
            PlacesSearchResponse response = PlacesApi.nearbySearchQuery(context, locationLatLng)
                .radius(1000) // 1km 반경
                .type(PlaceType.LOCAL_GOVERNMENT_OFFICE) // 지방 정부 사무소 (예: 시청, 군청 등)
                .type(PlaceType.POLICE) // 경찰서
                .type(PlaceType.FIRE_STATION)   // 소방서
                .type(PlaceType.POST_OFFICE) // 우체국
                .type(PlaceType.SCHOOL) // 초등학교, 중학교, 고등학교와 같은 교육 시설
                .type(PlaceType.HOSPITAL)  // 병원
                .type(PlaceType.UNIVERSITY) // 대학교
                .await();

            if (response.results != null) {
                totalCount = response.results.length;
                System.out.println("Found " + totalCount + " facilities"); // 디버깅용 로그
            }

            context.shutdown();
            
            location.setPublicFacilitiesCount(totalCount);
            location.setScore(calculateScore(totalCount));
            location.setAnalysis(generateAnalysis(location.getScore(), totalCount));
            
            // 디버깅용 로그
            System.out.println("Latitude: " + latitude);
            System.out.println("Longitude: " + longitude);
            System.out.println("Facilities count: " + totalCount);
            System.out.println("Score: " + location.getScore());
            
            return locationRepository.save(location);
            
        } catch (Exception e) {
            e.printStackTrace();
            // 에러 발생 시 기본값 설정
            location.setPublicFacilitiesCount(0);
            location.setScore(20);
            location.setAnalysis("분석 중 오류가 발생했습니다.");
            return locationRepository.save(location);
        }
    }
    
    private int calculateScore(int publicFacilitiesCount) {
        System.out.println("Calculating score for " + publicFacilitiesCount + " facilities"); // 디버깅용 로그
        
        if (publicFacilitiesCount >= 10) {
            return 100;
        } else if (publicFacilitiesCount >= 7) {
            return 80;
        } else if (publicFacilitiesCount >= 5) {
            return 60;
        } else if (publicFacilitiesCount >= 3) {
            return 40;
        } else {
            return 20;
        }
    }
    
    private String generateAnalysis(int score, int publicFacilitiesCount) {
        StringBuilder analysis = new StringBuilder();
        analysis.append("이 위치에서 1km 반경 내에 ").append(publicFacilitiesCount).append("개의 공공기관이 있습니다.\n");
        
        if (score >= 80) {
            analysis.append("매우 좋은 입지입니다. 다양한 공공시설과의 접근성이 뛰어납니다.");
        } else if (score >= 60) {
            analysis.append("괜찮은 입지입니다. 적절한 수의 공공시설이 있습니다.");
        } else if (score >= 40) {
            analysis.append("보통의 입지입니다. 기본적인 공공시설은 있으나 개선의 여지가 있습니다.");
        } else {
            analysis.append("개선이 필요한 입지입니다. 공공시설과의 접근성이 떨어집니다.");
        }
        
        return analysis.toString();
    }
} 