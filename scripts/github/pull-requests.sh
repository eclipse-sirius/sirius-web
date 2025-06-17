################################################################################
# Copyright (c) 2025 Obeo.
# This program and the accompanying materials
# are made available under the terms of the Eclipse Public License v2.0
# which accompanies this distribution, and is available at
# https://www.eclipse.org/legal/epl-2.0/
#
# SPDX-License-Identifier: EPL-2.0
#
# Contributors:
#     Obeo - initial API and implementation
#################################################################################
gh api graphql --paginate -F owner='eclipse-sirius' -F name='sirius-web' -f query='
  query($owner: String!, $name: String!, $endCursor: String) {
    repository(owner: $owner, name: $name) {
      pullRequests(first: 100, after: $endCursor) {
        nodes {
          number
          title
          state
          createdAt
          mergedAt
          milestone {
            title
          }
          labels(first: 20) {
            nodes {
              name
              color
            }
          }
          closingIssuesReferences(first: 20) {
            nodes {
              number
            }
          }
        }
        pageInfo {
          hasNextPage
          endCursor
        }
      }
    }
  }
'