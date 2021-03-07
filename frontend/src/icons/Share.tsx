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

export const Share = ({ title, ...props }) => {
  return (
    <svg
      xmlns="http://www.w3.org/2000/svg"
      viewBox="0 0 23 24"
      aria-labelledby="title"
      aria-describedby="desc"
      role="img"
      stroke="none"
      {...props}>
      <title>{title}</title>
      <path d="M13.9895 18.6577L7.53516 15.1375C6.91561 15.7525 6.12786 16.1703 5.27121 16.3382C4.41456 16.5061 3.52736 16.4167 2.72148 16.0811C1.91559 15.7456 1.22709 15.1789 0.742768 14.4527C0.25845 13.7264 0 12.873 0 12C0 11.127 0.25845 10.2736 0.742768 9.54735C1.22709 8.82106 1.91559 8.25442 2.72148 7.91887C3.52736 7.58331 4.41456 7.49386 5.27121 7.6618C6.12786 7.82973 6.91561 8.24753 7.53516 8.86251L13.9895 5.34231C13.7681 4.30373 13.928 3.22017 14.4399 2.28977C14.9518 1.35938 15.7815 0.644334 16.7772 0.275392C17.773 -0.0935509 18.8683 -0.0917338 19.8628 0.280511C20.8573 0.652756 21.6847 1.37055 22.1935 2.30264C22.7022 3.23472 22.8585 4.31881 22.6337 5.35666C22.4089 6.3945 21.818 7.31674 20.9691 7.95474C20.1203 8.59274 19.0701 8.90385 18.0107 8.83119C16.9512 8.75852 15.9534 8.30694 15.1995 7.55902L8.74519 11.0792C8.87394 11.6863 8.87394 12.3137 8.74519 12.9208L15.1995 16.441C15.9534 15.6931 16.9512 15.2415 18.0107 15.1688C19.0701 15.0962 20.1203 15.4073 20.9691 16.0453C21.818 16.6833 22.4089 17.6055 22.6337 18.6433C22.8585 19.6812 22.7022 20.7653 22.1935 21.6974C21.6847 22.6294 20.8573 23.3472 19.8628 23.7195C18.8683 24.0917 17.773 24.0936 16.7772 23.7246C15.7815 23.3557 14.9518 22.6406 14.4399 21.7102C13.928 20.7798 13.7681 19.6963 13.9895 18.6577Z"></path>
    </svg>
  );
};

Share.propTypes = propTypes;
