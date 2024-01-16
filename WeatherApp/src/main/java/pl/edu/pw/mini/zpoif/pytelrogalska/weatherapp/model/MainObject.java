
package pl.edu.pw.mini.zpoif.pytelrogalska.weatherapp.model;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import javax.annotation.processing.Generated;
import com.fasterxml.jackson.annotation.JsonAnyGetter;
import com.fasterxml.jackson.annotation.JsonAnySetter;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;

@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonPropertyOrder({
    "cod",
    "message",
    "cnt",
    "list",
    "city"
})
@Generated("jsonschema2pojo")
public class MainObject {

    @JsonProperty("cod")
    private String cod;
    @JsonProperty("message")
    private Integer message;
    @JsonProperty("cnt")
    private Integer cnt;
    @JsonProperty("list")
    private java.util.List<List> list = new ArrayList<>();
    @JsonProperty("city")
    private City city;
    @JsonIgnore
    private Map<String, Object> additionalProperties = new HashMap<String, Object>();

    @JsonProperty("cod")
    public String getCod() {
        return cod;
    }

    @JsonProperty("cod")
    public void setCod(String cod) {
        this.cod = cod;
    }

    public MainObject withCod(String cod) {
        this.cod = cod;
        return this;
    }

    @JsonProperty("message")
    public Integer getMessage() {
        return message;
    }

    @JsonProperty("message")
    public void setMessage(Integer message) {
        this.message = message;
    }

    public MainObject withMessage(Integer message) {
        this.message = message;
        return this;
    }

    @JsonProperty("cnt")
    public Integer getCnt() {
        return cnt;
    }

    @JsonProperty("cnt")
    public void setCnt(Integer cnt) {
        this.cnt = cnt;
    }

    public MainObject withCnt(Integer cnt) {
        this.cnt = cnt;
        return this;
    }

    @JsonProperty("list")
    public java.util.List<List> getList() {
        return list;
    }

    @JsonProperty("list")
    public void setList(java.util.List<List> list) {
        this.list = list;
    }

    public MainObject withList(java.util.List<List> list) {
        this.list = list;
        return this;
    }

    @JsonProperty("city")
    public City getCity() {
        return city;
    }

    @JsonProperty("city")
    public void setCity(City city) {
        this.city = city;
    }

    public MainObject withCity(City city) {
        this.city = city;
        return this;
    }

    @JsonAnyGetter
    public Map<String, Object> getAdditionalProperties() {
        return this.additionalProperties;
    }

    @JsonAnySetter
    public void setAdditionalProperty(String name, Object value) {
        this.additionalProperties.put(name, value);
    }

    public MainObject withAdditionalProperty(String name, Object value) {
        this.additionalProperties.put(name, value);
        return this;
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append(MainObject.class.getName()).append('@').append(Integer.toHexString(System.identityHashCode(this))).append('[');
        sb.append("cod");
        sb.append('=');
        sb.append(((this.cod == null)?"<null>":this.cod));
        sb.append(',');
        sb.append("message");
        sb.append('=');
        sb.append(((this.message == null)?"<null>":this.message));
        sb.append(',');
        sb.append("cnt");
        sb.append('=');
        sb.append(((this.cnt == null)?"<null>":this.cnt));
        sb.append(',');
        sb.append("list");
        sb.append('=');
        sb.append(((this.list == null)?"<null>":this.list));
        sb.append(',');
        sb.append("city");
        sb.append('=');
        sb.append(((this.city == null)?"<null>":this.city));
        sb.append(',');
        sb.append("additionalProperties");
        sb.append('=');
        sb.append(((this.additionalProperties == null)?"<null>":this.additionalProperties));
        sb.append(',');
        if (sb.charAt((sb.length()- 1)) == ',') {
            sb.setCharAt((sb.length()- 1), ']');
        } else {
            sb.append(']');
        }
        return sb.toString();
    }

    @Override
    public int hashCode() {
        int result = 1;
        result = ((result* 31)+((this.cnt == null)? 0 :this.cnt.hashCode()));
        result = ((result* 31)+((this.cod == null)? 0 :this.cod.hashCode()));
        result = ((result* 31)+((this.additionalProperties == null)? 0 :this.additionalProperties.hashCode()));
        result = ((result* 31)+((this.message == null)? 0 :this.message.hashCode()));
        result = ((result* 31)+((this.list == null)? 0 :this.list.hashCode()));
        result = ((result* 31)+((this.city == null)? 0 :this.city.hashCode()));
        return result;
    }

    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }
        if ((other instanceof MainObject) == false) {
            return false;
        }
        MainObject rhs = ((MainObject) other);
        return (((((((this.cnt == rhs.cnt)||((this.cnt!= null)&&this.cnt.equals(rhs.cnt)))&&((this.cod == rhs.cod)||((this.cod!= null)&&this.cod.equals(rhs.cod))))&&((this.additionalProperties == rhs.additionalProperties)||((this.additionalProperties!= null)&&this.additionalProperties.equals(rhs.additionalProperties))))&&((this.message == rhs.message)||((this.message!= null)&&this.message.equals(rhs.message))))&&((this.list == rhs.list)||((this.list!= null)&&this.list.equals(rhs.list))))&&((this.city == rhs.city)||((this.city!= null)&&this.city.equals(rhs.city))));
    }

}
