package com.ssa.Klozerz.dto;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;

@JsonInclude(Include.NON_NULL)
public class LeadDTO {

	 String id;
	 String name;
	 String type;
	 String subType;
//	 String drName;
//	 String drSpeciality;
	 String npi;
	 String whoisthedecisionmaker;
	 String address1;
	 String address2;
	 String city;
	 String state;
	 String zipCode;
	 String phone;
	 String fax;
	 String email;
	 String website;
	 String facilitySize;
	 String facilityNotes;
//	 String couldyougiveusanopportunity;
//	 String whatcouldIdotomakeyourjobeasier;
	 String howcanIearnyourtrustandbusiness;
//	 String thebiggestchallengestoReferrals;
//	 String mostimportantreferralconsideration;
//	 String patientsonafirstnamebasis;
//	 String patientfrequentlyvisitER;
//	 String cancelingapptsDuetotranspotation;
	 String leadSource;
	 String rating;
	 String status;
	 String payorType;
	 String ofmonthlyreferrals;
	 String contactLink;
	 String gpsPunch;
	 String user_id;
	 String suggestedFrequency;
	 
/**/
	 String created_by;
	 String created_date;
	 String modified_by;
	 String modified_date;
	 String archived_date;
	 String archived_by;	 
	 String archive_status;
/**/	 

//	 String accountType_2;
	 String suggestedRating;
	 String lastActivityBy;
	 String lastActivityDate;
	 
//	 String beds;
//	 String patients;
//	 String challengeswithagenciesyoureutilizing;
//	 String howmanycouldbeavoidednursecall;
//	 String numberOnePriority;
//	 String thingstheycallforregularly;
//	 String whichpatientshavedoconspeeddial;
//	 String wouldyousendusyournextclient;
//	 String independentorwithCaseMgnmt_Company;
//	 String otherSubFacilityType;
	 String contact_role;
	 
//	 String hMOPayor;
//	 String bCBSPayor;
//	 String medicarePayor;
//	 String otherPayorOne;
//	 String otherPayorTwo;
//	 String otherPayorThree;
	 String lead_to_account;
	 String biggestchallengeswithagenciesyoureutilizing;
	 String yourpatienthavefrequentvisittoER;
	 String ervisitscouldbeavoidedbyanursecall;
	 String appointmentscanceledduetotransportation;
	 String patientscalltheofficemorethanonceperweek;
	 String themostimportantthingtoconsiderduringagencyreference;
	 String howcanImakeyourjobeasier;
	 String whatthebesttimeformetocomeandvisit; 
	 String patientsyouseeperdayonanaverage;
	 String howoftendoyouuseHomeHealthpermonthonaverage;
	 String doyouwanttogiveusachancetoshowwhatwecando;
	 /**/
	 String progressNoteLastModifiedDate;
	 /* not in use but depending on query */ 
	 String howoftendoyoushiftfornursing;
	 String whatsizeisyouractivecaseload;
	 /**/
	 String howoftenyourefertoprivateduty;
	 String howoftendoyourefertotransportation;
	 String officeHours;
	 String mergeStatus;
	 String idandstatus;
	 
/* Accounts*/	 
	    /**/
	    String leadId;
	    /**/
	    String otherSubType; /* storing subType value*/
//	    String whatcouldIdotomakeyourjobeasier;  replaced with 'How can I make your job easier?'
//	    String whatisthebesttimeformetocomeandvisit; replace with 'whatthebesttimeformetocomeandvisit'
/**/
	    
 public LeadDTO()
 {
 }

 public String getMergeStatus()
 {
     return mergeStatus;
 }

 public void setMergeStatus(String mergeStatus)
 {
     this.mergeStatus = mergeStatus;
 }

 public String getProgressNoteLastModifiedDate()
 {
     return progressNoteLastModifiedDate;
 }

 public String getHowoftendoyoushiftfornursing()
 {
     return howoftendoyoushiftfornursing;
 }

 public void setHowoftendoyoushiftfornursing(String howoftendoyoushiftfornursing)
 {
     this.howoftendoyoushiftfornursing = howoftendoyoushiftfornursing;
 }

 public String getWhatsizeisyouractivecaseload()
 {
     return whatsizeisyouractivecaseload;
 }

 public void setWhatsizeisyouractivecaseload(String whatsizeisyouractivecaseload)
 {
     this.whatsizeisyouractivecaseload = whatsizeisyouractivecaseload;
 }

 public String getHowoftenyourefertoprivateduty()
 {
     return howoftenyourefertoprivateduty;
 }

 public void setHowoftenyourefertoprivateduty(String howoftenyourefertoprivateduty)
 {
     this.howoftenyourefertoprivateduty = howoftenyourefertoprivateduty;
 }

 public String getHowoftendoyourefertotransportation()
 {
     return howoftendoyourefertotransportation;
 }

 public void setHowoftendoyourefertotransportation(String howoftendoyourefertotransportation)
 {
     this.howoftendoyourefertotransportation = howoftendoyourefertotransportation;
 }

 public String getOfficeHours()
 {
     return officeHours;
 }

 public void setOfficeHours(String officeHours)
 {
     this.officeHours = officeHours;
 }

 public void setProgressNoteLastModifiedDate(String progressNoteLastModifiedDate)
 {
     this.progressNoteLastModifiedDate = progressNoteLastModifiedDate;
 }

 public String getIdandstatus()
 {
     return idandstatus;
 }

 public void setIdandstatus(String idandstatus)
 {
     this.idandstatus = idandstatus;
 }

 public String getSuggestedFrequency()
 {
     return suggestedFrequency;
 }

 public void setSuggestedFrequency(String suggestedFrequency)
 {
     this.suggestedFrequency = suggestedFrequency;
 }

 public String getBiggestchallengeswithagenciesyoureutilizing()
 {
     return biggestchallengeswithagenciesyoureutilizing;
 }

 public void setBiggestchallengeswithagenciesyoureutilizing(String biggestchallengeswithagenciesyoureutilizing)
 {
    this.biggestchallengeswithagenciesyoureutilizing = biggestchallengeswithagenciesyoureutilizing;
 }

 public String getYourpatienthavefrequentvisittoER()
 {
     return yourpatienthavefrequentvisittoER;
 }

 public void setYourpatienthavefrequentvisittoER(String yourpatienthavefrequentvisittoER)
 {
     this.yourpatienthavefrequentvisittoER = yourpatienthavefrequentvisittoER;
 }

 public String getERVisitscouldbeavoidedbyanursecall()
 {
     return this.ervisitscouldbeavoidedbyanursecall;
 }

 public void setERVisitscouldbeavoidedbyanursecall(String ervisitscouldbeavoidedbyanursecall)
 {
    this.ervisitscouldbeavoidedbyanursecall = ervisitscouldbeavoidedbyanursecall;
 }

 public String getAppointmentscanceledduetotransportation()
 {
     return appointmentscanceledduetotransportation;
 }

 public void setAppointmentscanceledduetotransportation(String appointmentscanceledduetotransportation)
 {
     this.appointmentscanceledduetotransportation = appointmentscanceledduetotransportation;
 }

 public String getPatientscalltheofficemorethanonceperweek()
 {
     return patientscalltheofficemorethanonceperweek;
 }

 public void setPatientscalltheofficemorethanonceperweek(String patientscalltheofficemorethanonceperweek)
 {
     this.patientscalltheofficemorethanonceperweek = patientscalltheofficemorethanonceperweek;
 }

 public String getThemostimportantthingtoconsiderduringagencyreference()
 {
     return themostimportantthingtoconsiderduringagencyreference;
 }

 public void setThemostimportantthingtoconsiderduringagencyreference(String themostimportantthingtoconsiderduringagencyreference)
 {
     this.themostimportantthingtoconsiderduringagencyreference = themostimportantthingtoconsiderduringagencyreference;
 }

 public String getHowcanImakeyourjobeasier()
 {
     return howcanImakeyourjobeasier;
 }

 public void setHowcanImakeyourjobeasier(String howcanImakeyourjobeasier)
 {
     this.howcanImakeyourjobeasier = howcanImakeyourjobeasier;
 }

 public String getWhatthebesttimeformetocomeandvisit()
 {
     return whatthebesttimeformetocomeandvisit;
 }

 public void setWhatthebesttimeformetocomeandvisit(String whatthebesttimeformetocomeandvisit)
 {
     this.whatthebesttimeformetocomeandvisit = whatthebesttimeformetocomeandvisit;
 }

 public String getPatientsyouseeperdayonanaverage()
 {
     return patientsyouseeperdayonanaverage;
 }

 public void setPatientsyouseeperdayonanaverage(String patientsyouseeperdayonanaverage)
 {
     this.patientsyouseeperdayonanaverage = patientsyouseeperdayonanaverage;
 }

 public String getHowoftendoyouuseHomeHealthpermonthonaverage()
 {
     return howoftendoyouuseHomeHealthpermonthonaverage;
 }

 public void setHowoftendoyouuseHomeHealthpermonthonaverage(String howoftendoyouuseHomeHealthpermonthonaverage)
 {
     this.howoftendoyouuseHomeHealthpermonthonaverage = howoftendoyouuseHomeHealthpermonthonaverage;
 }

 public String getDoyouwanttogiveusachancetoshowwhatwecando()
 {
     return doyouwanttogiveusachancetoshowwhatwecando;
 }

 public void setDoyouwanttogiveusachancetoshowwhatwecando(String doyouwanttogiveusachancetoshowwhatwecando)
 {
     this.doyouwanttogiveusachancetoshowwhatwecando = doyouwanttogiveusachancetoshowwhatwecando;
 }

// public String getHMOPayor()
// {
//     return hMOPayor;
// }

 public String getLead_to_account()
 {
     return lead_to_account;
 }

 public void setLead_to_account(String lead_to_account)
 {
     this.lead_to_account = lead_to_account;
 }

// public void setHMOPayor(String hMOPayor)
// {
//    this.hMOPayor = hMOPayor;
// }
//
// public String getBCBSPayor()
// {
//     return bCBSPayor;
// }
//
// public void setBCBSPayor(String bCBSPayor)
// {
//     this.bCBSPayor = bCBSPayor;
// }
//
// public String getMedicarePayor()
// {
//     return medicarePayor;
// }
//
// public void setMedicarePayor(String medicarePayor)
// {
//     this.medicarePayor = medicarePayor;
// }
//
// public String getOtherPayorOne()
// {
//     return otherPayorOne;
// }
//
// public void setOtherPayorOne(String otherPayorOne)
// {
//     this.otherPayorOne = otherPayorOne;
// }
//
// public String getOtherPayorTwo()
// {
//     return otherPayorTwo;
// }
//
// public void setOtherPayorTwo(String otherPayorTwo)
// {
//     this.otherPayorTwo = otherPayorTwo;
// }
//
// public String getOtherPayorThree()
// {
//     return otherPayorThree;
// }
//
// public void setOtherPayorThree(String otherPayorThree)
// {
//     this.otherPayorThree = otherPayorThree;
// }

// public String getOtherSubFacilityType()
// {
//     return otherSubFacilityType;
// }
//
// public void setOtherSubFacilityType(String otherSubFacilityType)
// {
//     this.otherSubFacilityType = otherSubFacilityType;
// }

 public String getContact_role()
 {
     return contact_role;
 }

 public void setContact_role(String contact_role)
 {
     this.contact_role = contact_role;
 }

// public String getAccountType_2()
// {
//     return accountType_2;
// }
//
// public void setAccountType_2(String accountType_2)
// {
//     this.accountType_2 = accountType_2;
// }

 public String getSuggestedRating()
 {
     return suggestedRating;
 }

 public void setSuggestedRating(String suggestedRating)
 {
     this.suggestedRating = suggestedRating;
 }

 public String getLastActivityBy()
 {
     return lastActivityBy;
 }

 public void setLastActivityBy(String lastActivityBy)
 {
     this.lastActivityBy = lastActivityBy;
 }

 public String getLastActivityDate()
 {
     return lastActivityDate;
 }

 public void setLastActivityDate(String lastActivityDate)
 {
     this.lastActivityDate = lastActivityDate;
 }

// public String getBeds()
// {
//     return beds;
// }
//
// public void setBeds(String beds)
// {
//     this.beds = beds;
// }
//
// public String getPatients()
// {
//     return patients;
// }
//
// public void setPatients(String patients)
// {
//     this.patients = patients;
// }

// public String getChallengeswithagenciesyoureutilizing()
// {
//     return challengeswithagenciesyoureutilizing;
// }
//
// public void setChallengeswithagenciesyoureutilizing(String challengeswithagenciesyoureutilizing)
// {
//     this.challengeswithagenciesyoureutilizing = challengeswithagenciesyoureutilizing;
// }

// public String getHowmanycouldbeavoidednursecall()
// {
//     return howmanycouldbeavoidednursecall;
// }
//
// public void setHowmanycouldbeavoidednursecall(String howmanycouldbeavoidednursecall)
// {
//     this.howmanycouldbeavoidednursecall = howmanycouldbeavoidednursecall;
// }

// public String getNumberOnePriority()
// {
//     return numberOnePriority;
// }
//
// public void setNumberOnePriority(String numberOnePriority)
// {
//     this.numberOnePriority = numberOnePriority;
// }

// public String getThingstheycallforregularly()
// {
//     return thingstheycallforregularly;
// }
//
// public void setThingstheycallforregularly(String thingstheycallforregularly)
// {
//     this.thingstheycallforregularly = thingstheycallforregularly;
// }

// public String getWhichpatientshavedoconspeeddial()
// {
//     return whichpatientshavedoconspeeddial;
// }
//
// public void setWhichpatientshavedoconspeeddial(String whichpatientshavedoconspeeddial)
// {
//     this.whichpatientshavedoconspeeddial = whichpatientshavedoconspeeddial;
// }

// public String getWouldyousendusyournextclient()
// {
//     return wouldyousendusyournextclient;
// }
//
// public void setWouldyousendusyournextclient(String wouldyousendusyournextclient)
// {
//     this.wouldyousendusyournextclient = wouldyousendusyournextclient;
// }

// public String getIndependentorwithCaseMgnmt_Company()
// {
//     return independentorwithCaseMgnmt_Company;
// }
//
// public void setIndependentorwithCaseMgnmt_Company(String independentorwithCaseMgnmt_Company)
// {
//     this.independentorwithCaseMgnmt_Company = independentorwithCaseMgnmt_Company;
// }

 public String getId()
 {
     return id;
 }

 public void setId(String id)
 {
     this.id = id;
 }

 public String getName()
 {
     return this.name;
 }

 public void setName(String name)
 {
     this.name = name;
 }

 public String getType()
 {
     return this.type;
 }

 public void setType(String type)
 {
	 this.type = type;
 }

 public String getSubType()
 {
     return this.subType;
 }

 public void setSubType(String subType)
 {
     this.subType = subType;
 }

// public String getDrName()
// {
//     return drName;
// }
//
// public void setDrName(String drName)
// {
//     this.drName = drName;
// }
//
// public String getDrSpeciality()
// {
//     return drSpeciality;
// }
//
// public void setDrSpeciality(String drSpeciality)
// {
//     this.drSpeciality = drSpeciality;
// }
//
 public String getNPI()
 {
     return npi;
 }

 public void setNPI(String npi)
 {
     this.npi = npi;
 }

 public String getWhoisthedecisionmaker()
 {
     return whoisthedecisionmaker;
 }

 public void setWhoisthedecisionmaker(String whoisthedecisionmaker)
 {
     this.whoisthedecisionmaker = whoisthedecisionmaker;
 }

 public String getAddress1()
 {
     return address1;
 }

 public void setAddress1(String address1)
 {
     this.address1 = address1;
 }

 public String getAddress2()
 {
     return address2;
 }

 public void setAddress2(String address2)
 {
     this.address2 = address2;
 }

 public String getCity()
 {
     return city;
 }

 public void setCity(String city)
 {
     this.city = city;
 }

 public String getState()
 {
     return state;
 }

 public void setState(String state)
 {
     this.state = state;
 }

 public String getZipCode()
 {
     return zipCode;
 }

 public void setZipCode(String zipCode)
 {
     this.zipCode = zipCode;
 }

 public String getPhone()
 {
     return phone;
 }

 public void setPhone(String phone)
 {
     this.phone = phone;
 }

 public String getFax()
 {
     return fax;
 }

 public void setFax(String fax)
 {
     this.fax = fax;
 }

 public String getEmail()
 {
     return this.email;
 }

 public void setEmail(String email)
 {
     this.email = email;
 }

 public String getWebsite()
 {
     return website;
 }

 public void setWebsite(String website)
 {
     this.website = website;
 }

 public String getFacilitySize()
 {
     return facilitySize;
 }

 public void setFacilitySize(String facilitySize)
 {
     this.facilitySize = facilitySize;
 }

 public String getFacilityNotes()
 {
     return facilityNotes;
 }

 public void setFacilityNotes(String facilityNotes)
 {
     this.facilityNotes = facilityNotes;
 }

// public String getCouldyougiveusanopportunity()
// {
//     return couldyougiveusanopportunity;
// }
//
// public void setCouldyougiveusanopportunity(String couldyougiveusanopportunity)
// {
//     this.couldyougiveusanopportunity = couldyougiveusanopportunity;
// }

// public String getWhatcouldIdotomakeyourjobeasier()
// {
//     return whatcouldIdotomakeyourjobeasier;
// }
//
// public void setWhatcouldIdotomakeyourjobeasier(String whatcouldIdotomakeyourjobeasier)
// {
//     this.whatcouldIdotomakeyourjobeasier = whatcouldIdotomakeyourjobeasier;
// }

 public String getHowcanIearnyourtrustandbusiness()
 {
     return howcanIearnyourtrustandbusiness;
 }

 public void setHowcanIearnyourtrustandbusiness(String howcanIearnyourtrustandbusiness)
 {
     this.howcanIearnyourtrustandbusiness = howcanIearnyourtrustandbusiness;
 }

// public String getThebiggestchallengestoReferrals()
// {
//     return thebiggestchallengestoReferrals;
// }
//
// public void setThebiggestchallengestoReferrals(String thebiggestchallengestoReferrals)
// {
//     this.thebiggestchallengestoReferrals = thebiggestchallengestoReferrals;
// }

// public String getMostimportantreferralconsideration()
// {
//     return mostimportantreferralconsideration;
// }
//
// public void setMostimportantreferralconsideration(String mostimportantreferralconsideration)
// {
//    this.mostimportantreferralconsideration = mostimportantreferralconsideration;
// }

// public String getPatientsonafirstnamebasis()
// {
//     return patientsonafirstnamebasis;
// }
//
// public void setPatientsonafirstnamebasis(String patientsonafirstnamebasis)
// {
//     this.patientsonafirstnamebasis = patientsonafirstnamebasis;
// }

// public String getPatientfrequentlyvisitER()
// {
//     return patientfrequentlyvisitER;
// }
//
// public void setPatientfrequentlyvisitER(String patientfrequentlyvisitER)
// {
//     this.patientfrequentlyvisitER = patientfrequentlyvisitER;
// }

// public String getCancelingapptsDuetotranspotation()
// {
//     return cancelingapptsDuetotranspotation;
// }
//
// public void setCancelingapptsDuetotranspotation(String cancelingapptsDuetotranspotation)
// {
//     this.cancelingapptsDuetotranspotation = cancelingapptsDuetotranspotation;
// }

 public String getLeadSource()
 {
     return leadSource;
 }

 public void setLeadSource(String leadSource)
 {
     this.leadSource = leadSource;
 }

 public String getRating()
 {
     return this.rating;
 }

 public void setRating(String rating)
 {
     this.rating = rating;
 }

 public String getStatus()
 {
     return this.status;
 }

 public void setStatus(String status)
 {
     this.status = status;
 }

 public String getPayorType()
 {
     return payorType;
 }

 public void setPayorType(String payorType)
 {
     this.payorType = payorType;
 }

 public String getOfmonthlyreferrals()
 {
     return ofmonthlyreferrals;
 }

 public void setOfmonthlyreferrals(String ofmonthlyreferrals)
 {
     this.ofmonthlyreferrals = ofmonthlyreferrals;
 }

 public String getContactLink()
 {
     return contactLink;
 }

 public void setContactLink(String contactLink)
 {
     this.contactLink = contactLink;
 }

 public String getGpsPunch()
 {
     return gpsPunch;
 }

 public void setGpsPunch(String gpsPunch)
 {
     this.gpsPunch = gpsPunch;
 }

 public String getCreated_by()
 {
     return created_by;
 }

 public void setCreated_by(String created_by)
 {
     this.created_by = created_by;
 }

 public String getCreated_date()
 {
     return created_date;
 }

 public void setCreated_date(String created_date)
 {
     this.created_date = created_date;
 }

 public String getModified_by()
 {
     return modified_by;
 }

 public void setModified_by(String modified_by)
 {
     this.modified_by = modified_by;
 }

 public String getModified_date()
 {
     return modified_date;
 }

 public void setModified_date(String modified_date)
 {
     this.modified_date = modified_date;
 }

 public String getArchived_date()
 {
     return archived_date;
 }

 public void setArchived_date(String archived_date)
 {
     this.archived_date = archived_date;
 }

 public String getArchived_by()
 {
     return archived_by;
 }

 public void setArchived_by(String archived_by)
 {
     this.archived_by = archived_by;
 }

 public String getUser_id()
 {
     return user_id;
 }

 public void setUser_id(String user_id)
 {
     this.user_id = user_id;
 }

 public String getArchive_status()
 {
     return archive_status;
 }

 public void setArchive_status(String archive_status)
 {
     this.archive_status = archive_status;
 }

}
