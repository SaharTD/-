Mohassil is a comprehensive digital platform for managing and collecting taxes in Saudi Arabia. It is designed to streamline invoicing and tax workflows for businesses of all sizes. Core features include invoice generation and confirmation, automatic 15% VAT calculation, exportable tax reports in PDF format, real-time email and WhatsApp notifications, and automatic penalty enforcement for late submissions. The system also delivers analytical insights and calculates annual net profit with precision.

Users: 
•  Admin
•	Responsible for high-level management and administrative tasks, such as activating tax payers and adding auditors.
•  Auditor
•	Focuses on regulatory and tax-related tasks, such as creating, approving, and managing tax reports and handling business compliance issues.
•  Tax Payer
•	Represents business owners or individuals responsible for managing their businesses' financial and tax-related activities, including adding accountants, managing branches, and viewing revenues.
•  Accountant
•	Handles day-to-day financial operations for businesses, such as managing sales, restocking products, and operating counter boxes.


This project features a comprehensive set of RESTful API endpoints designed for managing business operations,
including accounting, sales, auditing, and branch management. The implementation showcases my experience in backend development.
Below is a detailed list of the endpoints I developed and their functionalities.


GET /api/v1/accountant/by-branch/{branchId}

Developed the endpoint to fetch accountants assigned to a specific branch.

GET /api/v1/accountant/by-branch/{branchId}

Implemented another variation to retrieve accountants associated with a branch.

PUT /api/v1/admin/activate/{taxPayerId}

Built functionality to activate a taxpayer via the admin panel.

PUT /api/v1/auditor/activate-business/{taxPayerId}/{businessId}

Enabled auditors to activate businesses registered under a taxpayer.

PUT /api/v1/auditor/block-business/{taxPayerId}/{businessId}

Added the ability for auditors to block inactive or non-compliant businesses.

GET /api/v1/sales/branch-sales/{branchId}

Created an endpoint to retrieve all sales operations for a specific branch.

GET /api/v1/sales/business-sales/{businessId}

Designed a service to fetch all sales operations for a business.

GET /api/v1/business/my-businesses

Built functionality for users to view all businesses they manage.

GET /api/v1/business/branch-count/{businessId}

Developed a feature to count the total number of branches for a specific business.

GET /api/v1/business/business-revenue/{businessId}

Implemented the logic to calculate and display the total revenue for a business.

GET /api/v1/branch/branch-revenue/{branchId}

Created an endpoint to calculate and retrieve revenue for a specific branch.

PUT /api/v1/tax-payer/de-activate-accountant/{accountantId}

Developed functionality to block or deactivate an accountant when needed.

POST /api/v1/tax-payer/add-accountant/{businessId}

Built the endpoint for adding a new accountant to a specific business.

POST /api/v1/business/add-business

Designed the service to allow users to add new businesses.

POST /api/v1/sales/add-sale/{boxId}

Created functionality to add a sale record associated with a counter box.

DELETE /api/v1/item-sales/remove/{itemSaleId}/{saleId}

Implemented a feature to remove specific items from a sale.

GET /api/v1/sales/print-sale/{saleId}

Built the functionality to generate and print invoices for a specific sale.

PUT /api/v1/sales/confirm-sale/{saleId}

Added functionality to confirm a sale after verification.

