import http from 'k6/http';
import { check, sleep } from 'k6';

const baseUrl = __ENV.LOAD_TEST_BASE_URL || 'http://localhost:8080';
const duration = __ENV.LOAD_TEST_DURATION || '1m';

export const options = {
  scenarios: {
    rnf10_500_users: {
      executor: 'constant-vus',
      vus: 500,
      duration,
      gracefulStop: '30s',
    },
  },
  thresholds: {
    checks: ['rate>0.99'],
    http_req_failed: ['rate<0.01'],
    http_req_duration: ['p(95)<1000'],
  },
};

export default function () {
  const response = http.get(`${baseUrl}/actuator/health`);

  check(response, {
    'health endpoint returns 200': (res) => res.status === 200,
    'application reports UP': (res) => res.body.includes('"status":"UP"'),
  });

  sleep(1);
}
