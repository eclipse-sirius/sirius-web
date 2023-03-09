/*******************************************************************************
 * Copyright (c) 2023 Obeo.
 * This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v2.0
 * which accompanies this distribution, and is available at
 * https://www.eclipse.org/legal/epl-2.0/
 *
 * SPDX-License-Identifier: EPL-2.0
 *
 * Contributors:
 *     Obeo - initial API and implementation
 *******************************************************************************/

import { BorderBox } from '../model/BorderBox';
import { BorderNodesBox } from '../model/BorderNodesBox';
import { ContentBox } from '../model/ContentBox';
import { InsideLabelBox } from '../model/InsideLabelBox';
import { MarginBox } from '../model/MarginBox';
import { OutsideLabelBox } from '../model/OutsideLabelBox';

export const ImageNodeWithBottomOutsideLabel = () => {
  return (
    <MarginBox withBorder>
      <OutsideLabelBox label="Author" position="BOTTOM_CENTER">
        <BorderNodesBox>
          <BorderBox height="26px" width="26px" borderWidth={1}>
            <InsideLabelBox>
              <ContentBox childLayoutStrategy="LIST">
                <svg xmlns="http://www.w3.org/2000/svg" height="24" width="24">
                  <path d="M5 17.85q1.35-1.325 3.138-2.088Q9.925 15 12 15t3.863.762q1.787.763 3.137 2.088V5H5ZM12 13q1.45 0 2.475-1.025Q15.5 10.95 15.5 9.5q0-1.45-1.025-2.475Q13.45 6 12 6q-1.45 0-2.475 1.025Q8.5 8.05 8.5 9.5q0 1.45 1.025 2.475Q10.55 13 12 13Zm-9 8V3h18v18Zm4-2h10v-.25q-1.05-.875-2.325-1.312Q13.4 17 12 17t-2.675.438Q8.05 17.875 7 18.75Zm5-8q-.625 0-1.062-.438-.438-.437-.438-1.062t.438-1.062Q11.375 8 12 8t1.062.438q.438.437.438 1.062t-.438 1.062Q12.625 11 12 11Zm0-1.5Z" />
                </svg>
              </ContentBox>
            </InsideLabelBox>
          </BorderBox>
        </BorderNodesBox>
      </OutsideLabelBox>
    </MarginBox>
  );
};
