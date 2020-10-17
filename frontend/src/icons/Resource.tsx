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

export const Resource = ({ title, ...props }) => {
  return (
    <svg
      xmlns="http://www.w3.org/2000/svg"
      viewBox="0 0 22 24"
      aria-labelledby="title"
      aria-describedby="desc"
      role="img"
      {...props}>
      <title>{title}</title>
      <path d="M8.4 0C9.0624 0 9.6 0.5376 9.6 1.2V6C9.6 6.6624 9.0624 7.2 8.4 7.2H6V9.6H12V8.4C12 7.7376 12.5376 7.2 13.2 7.2H20.4C21.0624 7.2 21.6 7.7376 21.6 8.4V13.2C21.6 13.8624 21.0624 14.4 20.4 14.4H13.2C12.5376 14.4 12 13.8624 12 13.2V12H6V19.2H12V18C12 17.3376 12.5376 16.8 13.2 16.8H20.4C21.0624 16.8 21.6 17.3376 21.6 18V22.8C21.6 23.4624 21.0624 24 20.4 24H13.2C12.5376 24 12 23.4624 12 22.8V21.6H4.8C4.1376 21.6 3.6 21.0624 3.6 20.4V7.2H1.2C0.5376 7.2 0 6.6624 0 6V1.2C0 0.5376 0.5376 0 1.2 0H8.4Z"></path>
    </svg>
  );
};
Resource.propTypes = propTypes;
