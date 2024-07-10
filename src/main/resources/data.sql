INSERT INTO users (email, password, username, firstname, is_active, role, annual_income, created_at) VALUES
('spca@pawsomecare.com', '$2a$10$rPM94Snk2qrTm6L/EPcrVeZfWrrRWvYdTK2H2y/ymhfi5SrQdXDWa', 'SPCA', 'SPCA', true, 2, 0, now()),
('asa@pawsomecare.com', '$2a$10$rPM94Snk2qrTm6L/EPcrVeZfWrrRWvYdTK2H2y/ymhfi5SrQdXDWa', 'ASA', 'ASA', true, 3, 0, now()),
('ins@pawsomecare.com', '$2a$10$rPM94Snk2qrTm6L/EPcrVeZfWrrRWvYdTK2H2y/ymhfi5SrQdXDWa', 'PAW AGENT', 'PAW AGENT', true, 4, 0, now());

INSERT INTO public.user_roles(
	id, role_type)
	VALUES (1, 'CLIENT'), (2, 'ADMIN'), (3, 'VETERINARIAN'), (4, 'GROOMING');