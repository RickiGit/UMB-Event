package com.ricki.umbevents.Entities

public class GlobalVariable {
    var rootUrl = "http://umbevent.azurewebsites.net/"
    var webServiceCheckLogin = rootUrl + "api/LoginService/GetUserAccount"
    var webServiceRegister = rootUrl + "api/ParticipantService/RegistrationParticipant"
    var webServiceGetAllEvent = rootUrl + "api/EventService/GetAllEvents"
    var webServiceGetQuotaAvailable = rootUrl + "api/EventService/GetQuotaAvailable"
    var webServiceCheckStatusRegistration = rootUrl + "api/RegistrationService/StatusRegistration"
    var webServiceCheckDetailRegistration = rootUrl + "api/RegistrationService/GetDetailRegistration"
    var webServiceGetAllMyEvent = rootUrl + "api/EventService/GetAllMyEvents"
    var webServiceGetAllParticipantType = rootUrl + "api/ParticipantTypeService/GetAllParticipantType"
    var webServiceRegistrationEvent = rootUrl + "api/RegistrationService/RegistrationEvent"
    var webServiceGetStatusCheckin = rootUrl + "api/AttendanceService/CheckInOutStatus"
    var webServiceCheckin = rootUrl + "api/AttendanceService/CheckInParticipant"
    var webServiceCheckout = rootUrl + "api/AttendanceService/CheckOutParticipant"
    var webServiceGetStatusCertificate = rootUrl + "api/AttendanceService/CheckCertificateStatus"
}