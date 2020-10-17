/*******************************************************************************
 * Copyright (c) 2020 Remix Design Studio, Obeo and others
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 *
 * SPDX-License-Identifier: Apache-2.0
 *
 * Contributors:
 *     Remix Design Studio - initial implementation as SVG
 *     Obeo - conversion into JSX
 *******************************************************************************/
import React from 'react';
import PropTypes from 'prop-types';

const propTypes = {
  title: PropTypes.string.isRequired,
};

export const Delete = ({ title, ...props }) => {
  return (
    <svg
      xmlns="http://www.w3.org/2000/svg"
      viewBox="0 0 24 24"
      aria-labelledby="title"
      aria-describedby="desc"
      role="img"
      {...props}>
      <title>{title}</title>
      <path d="M18 4.8h6v2.4h-2.4v15.6a1.2 1.2 0 01-1.2 1.2H3.6a1.2 1.2 0 01-1.2-1.2V7.2H0V4.8h6V1.2A1.2 1.2 0 017.2 0h9.6A1.2 1.2 0 0118 1.2v3.6zm-9.6 6V18h2.4v-7.2H8.4zm4.8 0V18h2.4v-7.2h-2.4zM8.4 2.4v2.4h7.2V2.4H8.4z"></path>
    </svg>
  );
};

Delete.propTypes = propTypes;
