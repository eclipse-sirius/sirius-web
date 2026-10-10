#!/usr/bin/env bash

# Copyright (c) 2026 Obeo.
# This program and the accompanying materials
# are made available under the terms of the Eclipse Public License v2.0
# which accompanies this distribution, and is available at
# https://www.eclipse.org/legal/epl-2.0/
#
# SPDX-License-Identifier: EPL-2.0

set -euo pipefail

: "${GITHUB_REPOSITORY:?GITHUB_REPOSITORY must be set}"
: "${PR_AUTHOR:?PR_AUTHOR must be set}"

readonly MAX_OPEN_PULL_REQUESTS=3

open_pull_requests="$(
  gh api --method GET /search/issues \
    -f "q=repo:${GITHUB_REPOSITORY} is:pr is:open author:${PR_AUTHOR}" \
    -f per_page=1 \
    --jq '.total_count'
)"

if (( open_pull_requests > MAX_OPEN_PULL_REQUESTS )); then
  echo "::error::@${PR_AUTHOR} has now ${open_pull_requests} open pull requests. The maximum allowed is ${MAX_OPEN_PULL_REQUESTS}. Please ensure your existing pull requests are merged before creating another one."
  exit 1
fi
