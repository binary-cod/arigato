# Arigato E-commerce application

## Architecture

## AWS Environment credentials
to use AWS S3 storage you need to export yor AWS credentials as an an environment variabes as below:
```bash
export AWS_ACCESS_KEY_ID=xxxxx
export AWS_SECRET_ACCESS_KEY=xxxxx
```

## Stripe authentication credentials

in __application.properties__ you need to define variables below

```properties
stripe.keys.public=pk_test_
stripe.keys.secret=sk_test_
```