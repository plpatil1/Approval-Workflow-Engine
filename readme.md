# Configurable Approval Workflow Engine

## Overview
This project implements a configurable, database-driven approval workflow engine using Spring Boot.

## Key Features
- Multi-step approval flow
- Fully database-driven workflow
- Role-based authorization
- JWT security
- Admin override
- Complete audit history
- Transactional consistency

## Architecture
- Spring Boot
- Spring Data JPA
- H2 Database
- Spring Security + JWT

## Workflow Configuration
Approval steps are stored in `approval_step` table.
No approval logic is hardcoded in code.

## API Endpoints
- POST /requests
- GET /requests/{id}
- POST /requests/{id}/approve
- POST /requests/{id}/reject
- GET /requests/history/{id}

## Security
JWT-based authentication with roles:
REQUESTER, APPROVER, ADMIN

## How to Run
1. Clone repository
2. Run Spring Boot application
3. Access H2 Console at `/h2-console`
4. Insert workflow configuration
5. Use `/auth/token/{role}` to generate token

## Testing
JUnit and Mockito used.

<img width="1758" height="810" alt="image" src="https://github.com/user-attachments/assets/618d4b8b-188f-45d9-81e1-e1af184a1f47" />

Test coverage above 80%.

## Sample Workflow
- LEAVE: APPROVER → ADMIN
- EXPENSE: APPROVER → APPROVER → ADMIN
