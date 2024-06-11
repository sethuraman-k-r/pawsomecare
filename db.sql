CREATE TABLE "category" (
  "id" integer PRIMARY KEY,
  "name" varchar(30),
  "is_active" bool
);

CREATE TYPE user_role AS ENUM ('USER', 'ADMIN', 'ADMIN1');

CREATE TABLE "users" (
  "id" integer PRIMARY KEY,
  "firstname" varchar(30),
  "lastname" varchar(30),
  "dob" timestamp,
  "annual_income" float,
  "username" varchar(30),
  "email" varchar(30) UNIQUE NOT NULL,
  "created_at" timestamp,
  "password" varchar(100),
  "contact" varchar(12),
  "address" varchar(1000),
  "role" user_role,
  "is_active" bool,
  "updated_on" timestamp
);

CREATE TABLE "veterinarian" (
  "id" integer PRIMARY KEY,
  "name" varchar(30),
  "email" varchar(30),
  "contact_no" varchar(12),
  "is_active" bool,
  "consult_fee" float
);

CREATE TABLE "clinic" (
  "id" integer PRIMARY KEY,
  "name" varchar,
  "description" varchar(2000),
  "address" varchar(1000),
  "specialities" varchar(1000)
);

CREATE TABLE "appointment" (
  "id" integer PRIMARY KEY,
  "appt_time" timestamp,
  "clinic" integer,
  "vaccine" integer,
  "is_consult" bool,
  "is_vaccine" bool,
  "is_grooming" bool,
  "vaccine_dose" float,
  "vaccine_other" varchar(30),
  "consult_detail" varchar(1000),
  "tablet_prescribed" varchar(1000),
  "next_visit_suggest" timestamp,
  "feedback" integer,
  "appt_by" integer,
  "pet_id" integer,
  "vet_id" integer,
  "reason" varchar(1000),
  "created_at" timestamp,
  "updated_on" timestamp,
  "amount" float
);

CREATE TABLE "medicine" (
  "id" integer PRIMARY KEY,
  "name" varchar(30),
  "created_on" timestamp,
  "expires_at" timestamp,
  "per_cost" float,
  "description" varchar(1000),
  "is_ins_allowed" bool
);

CREATE TABLE "vaccine" (
  "id" integer PRIMARY KEY,
  "name" varchar(30),
  "description" varchar(200),
  "amount" float,
  "created_on" timestamp,
  "is_ins_allowed" bool
);

CREATE TABLE "feedback" (
  "id" integer PRIMARY KEY,
  "title" varchar(60),
  "description" text,
  "rate" integer,
  "appointment" integer,
  "created_at" timestamp
);

CREATE TYPE gender AS ENUM ('MALE', 'FEMALE', 'OTHERS');

CREATE TABLE "pet" (
  "id" integer PRIMARY KEY,
  "is_adopted" bool,
  "is_licensed" bool,
  "pet_name" varchar(30),
  "gender" gender,
  "weight" float,
  "age" integer,
  "dob" timestamp,
  "license_id" integer,
  "owner_id" integer,
  "category_id" integer,
  "insurance_id" integer,
  "created_at" timestamp,
  "updated_on" timestamp
);

CREATE TABLE "ins_pack_name" (
  "id" integer PRIMARY KEY,
  "pack_name" varchar(30),
  "type" varchar(30),
  "coverage" float,
  "amount" float,
  "validity_in_months" integer,
  "is_active" bool,
  "created_at" timestamp,
  "updated_on" timestamp,
  "description" varchar(2000)
);

CREATE TABLE "insurance" (
  "id" varchar(30) PRIMARY KEY,
  "pack_id" integer,
  "pet_id" integer,
  "created_at" timestamp,
  "updated_on" timestamp,
  "expired_at" timestamp,
  "feedback" integer
);

CREATE TABLE "insurance_claim" (
  "claim_id" integer PRIMARY KEY,
  "insurance_id" varchar(30),
  "claim_amount" float,
  "appt_id" integer,
  "is_settled" bool,
  "is_canceled" bool,
  "description" bool,
  "feedback" integer
);

CREATE TABLE "license_form" (
  "id" integer PRIMARY KEY,
  "pet_id" integer,
  "owner_id" integer,
  "license_no" varchar(30) UNIQUE,
  "issued_at" timestamp,
  "expires_on" timestamp,
  "is_active" bool,
  "amount" float
);

CREATE TABLE "adoption_form" (
  "id" integer PRIMARY KEY,
  "pet_id" integer,
  "owner_id" integer,
  "description" varchar(2000),
  "created_at" timestamp,
  "updated_on" timestamp,
  "approved_by" integer	
);

CREATE TABLE "grooming" (
  "id" integer PRIMARY KEY,
  "name" integer,
  "description" integer,
  "cost" float,
  "time_require" timestamp,
  "is_ins_allowed" bool
);

COMMENT ON COLUMN "feedback"."description" IS 'feedback detail';

COMMENT ON COLUMN "pet"."id" IS 'unique string';

ALTER TABLE "appointment" ADD FOREIGN KEY ("clinic") REFERENCES "clinic" ("id");

ALTER TABLE "appointment" ADD FOREIGN KEY ("vaccine") REFERENCES "vaccine" ("id");

ALTER TABLE "appointment" ADD FOREIGN KEY ("feedback") REFERENCES "feedback" ("id");

ALTER TABLE "appointment" ADD FOREIGN KEY ("appt_by") REFERENCES "users" ("id");

ALTER TABLE "appointment" ADD FOREIGN KEY ("pet_id") REFERENCES "pet" ("id");

ALTER TABLE "pet" ADD FOREIGN KEY ("owner_id") REFERENCES "users" ("id");

ALTER TABLE "license_form" ADD FOREIGN KEY ("pet_id") REFERENCES "pet" ("id");

ALTER TABLE "insurance" ADD FOREIGN KEY ("pack_id") REFERENCES "ins_pack_name" ("id");

ALTER TABLE "insurance" ADD FOREIGN KEY ("pet_id") REFERENCES "pet" ("id");

ALTER TABLE "insurance_claim" ADD FOREIGN KEY ("insurance_id") REFERENCES "insurance" ("id");

ALTER TABLE "insurance_claim" ADD FOREIGN KEY ("appt_id") REFERENCES "appointment" ("id");

ALTER TABLE "pet" ADD FOREIGN KEY ("category_id") REFERENCES "category" ("id");

ALTER TABLE "license_form" ADD FOREIGN KEY ("owner_id") REFERENCES "users" ("id");

ALTER TABLE "adoption_form" ADD FOREIGN KEY ("pet_id") REFERENCES "pet" ("id");

ALTER TABLE "adoption_form" ADD FOREIGN KEY ("owner_id") REFERENCES "users" ("id");

ALTER TABLE "adoption_form" ADD FOREIGN KEY ("approved_by") REFERENCES "users" ("id");

CREATE TABLE "grooming_appointment" (
  "grooming_id" integer,
  "appointment_id" integer,
  PRIMARY KEY ("grooming_id", "appointment_id")
);

ALTER TABLE "grooming_appointment" ADD FOREIGN KEY ("grooming_id") REFERENCES "grooming" ("id");

ALTER TABLE "grooming_appointment" ADD FOREIGN KEY ("appointment_id") REFERENCES "appointment" ("id");

CREATE TABLE "veterinarian_clinic" (
  "veterinarian_id" integer,
  "clinic_id" integer,
  PRIMARY KEY ("veterinarian_id", "clinic_id")
);

ALTER TABLE "veterinarian_clinic" ADD FOREIGN KEY ("veterinarian_id") REFERENCES "veterinarian" ("id");

ALTER TABLE "veterinarian_clinic" ADD FOREIGN KEY ("clinic_id") REFERENCES "clinic" ("id");

ALTER TABLE "appointment" ADD FOREIGN KEY ("vet_id") REFERENCES "veterinarian" ("id");

CREATE TABLE "appointment_medicine" (
  "appointment_id" integer,
  "medicine_id" integer,
  PRIMARY KEY ("appointment_id", "medicine_id")
);

ALTER TABLE "appointment_medicine" ADD FOREIGN KEY ("appointment_id") REFERENCES "appointment" ("id");

ALTER TABLE "appointment_medicine" ADD FOREIGN KEY ("medicine_id") REFERENCES "medicine" ("id");

CREATE TABLE "insurance_feedback" (
  "insurance_id" varchar(30),
  "feedback_id" integer,
  PRIMARY KEY ("insurance_id", "feedback_id")
);

ALTER TABLE "insurance_feedback" ADD FOREIGN KEY ("insurance_id") REFERENCES "insurance" ("id");

ALTER TABLE "insurance_feedback" ADD FOREIGN KEY ("feedback_id") REFERENCES "feedback" ("id");

ALTER TABLE "insurance_claim" ADD FOREIGN KEY ("feedback") REFERENCES "feedback" ("id");