package io.oasp.application.mtsj.ordermanagement.logic.api.to;

import java.util.List;

import io.oasp.application.mtsj.general.common.api.to.AbstractEto;
import io.oasp.application.mtsj.ordermanagement.common.api.Order;
import io.oasp.application.mtsj.ordermanagement.dataaccess.api.OrderLineEntity;

/**
 * Entity transport object of Order
 */
public class OrderEto extends AbstractEto implements Order {

  private static final long serialVersionUID = 1L;

  private Long idBooking;

  private Long idInvitedGuest;

  private List<OrderLineEntity> lines;

  private String token;

  public List<OrderLineEntity> getLines() {

    return this.lines;
  }

  public void setLines(List<OrderLineEntity> lines) {

    this.lines = lines;
  }

  @Override
  public Long getIdBooking() {

    return this.idBooking;
  }

  @Override
  public void setIdBooking(Long idBooking) {

    this.idBooking = idBooking;
  }

  @Override
  public Long getIdInvitedGuest() {

    return this.idInvitedGuest;
  }

  @Override
  public void setIdInvitedGuest(Long idInvitedGuest) {

    this.idInvitedGuest = idInvitedGuest;
  }

  @Override
  public int hashCode() {

    final int prime = 31;
    int result = super.hashCode();
    result = prime * result + ((this.idBooking == null) ? 0 : this.idBooking.hashCode());
    result = prime * result + ((this.idInvitedGuest == null) ? 0 : this.idInvitedGuest.hashCode());
    return result;
  }

  @Override
  public boolean equals(Object obj) {

    if (this == obj) {
      return true;
    }
    if (obj == null) {
      return false;
    }
    // class check will be done by super type EntityTo!
    if (!super.equals(obj)) {
      return false;
    }
    OrderEto other = (OrderEto) obj;
    if (this.idBooking == null) {
      if (other.idBooking != null) {
        return false;
      }
    } else if (!this.idBooking.equals(other.idBooking)) {
      return false;
    }
    if (this.idInvitedGuest == null) {
      if (other.idInvitedGuest != null) {
        return false;
      }
    } else if (!this.idInvitedGuest.equals(other.idInvitedGuest)) {
      return false;
    }
    return true;
  }

  /**
   * @return token
   */
  public String getToken() {

    return this.token;
  }

  /**
   * @param token new value of {@link #getToken}.
   */
  public void setToken(String token) {

    this.token = token;
  }
}
