/*
 * SPECjEnterprise2018 - a benchmark for enterprise middleware
 * Copyright 1995-2018 Standard Performance Evaluation Corporation
 * All Rights Reserved
 */

package org.jboss.perf.common;

/**
 * @author <a href="mailto:stale.pedersen@jboss.org">Ståle W. Pedersen</a>
 */
public enum Gender {
   MALE( "MALE" ),
   FEMALE( "FEMALE" )
   ;

   private String gender;

   Gender(String gender) {
      this.gender = gender;
   }

   public String toString() {
      return this.gender;
   }
}
