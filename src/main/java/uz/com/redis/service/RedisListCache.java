package uz.com.redis.service;

import org.springframework.data.redis.core.ListOperations;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;
import org.springframework.web.bind.annotation.PostMapping;
import uz.com.redis.dto.PersonDto;
import uz.com.redis.dto.RangeDto;

import javax.annotation.PostConstruct;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class RedisListCache {
    private final ListOperations<String, Object> listOperations;

    public RedisListCache(final RedisTemplate<String, Object> redisTemplate) {
        listOperations = redisTemplate.opsForList();
    }


    public void cachePersons(final String key, final List<PersonDto>personDtoList){
        for (final PersonDto personDto : personDtoList) {
            listOperations.leftPush(key,personDto);
        }
    }

    public List<PersonDto> getPersonsRange(final String key, final RangeDto rangeDto){
        final List<Object>objects=listOperations.range(key, rangeDto.getFrom(), rangeDto.getTo());
        if (CollectionUtils.isEmpty(objects)){
            return Collections.emptyList();
        }
        return objects.stream()
                .map(x->(PersonDto) x)
                .collect(Collectors.toList());
    }

    public PersonDto getLastElement(final String key){
        final Object o=listOperations.rightPop(key);
        if (o==null)
            return null;
        return (PersonDto) o;
    }

    public void trim(final String key,final RangeDto rangeDto){
        listOperations.trim(key,rangeDto.getFrom(), rangeDto.getTo());
    }


    @PostConstruct
    public void setup() {
        listOperations.leftPush("key", "hello there");
        System.out.println(listOperations.rightPop("key"));
    }

}
