/*******************************************************************************
 * Copyright (c) 2025 Obeo.
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
import { useMutation } from '@apollo/client';
import { useReporting } from '@eclipse-sirius/sirius-components-core';
import {
  GQLEditRectangularNodeApparenceData,
  GQLEditRectangularNodeApparenceVariables,
  GQLEditRectangularNodeAppearanceMutation,
  GQLResetRectangularNodeApparenceData,
  GQLResetRectangularNodeApparenceVariables,
  GQLResetRectangularNodeAppearanceMutation,
  useUpdateRectangularNodeAppearanceValue,
} from './useUpdateRectangularNodeAppearance.types';

export const useUpdateRectangularNodeAppearance = (
  editingContextId: string,
  representationId: string,
  nodeId: string
): useUpdateRectangularNodeAppearanceValue => {
  const [editRectangularNodeApparence, editRectangularNodeApparenceResult] = useMutation<
    GQLEditRectangularNodeApparenceData,
    GQLEditRectangularNodeApparenceVariables
  >(GQLEditRectangularNodeAppearanceMutation);

  useReporting(
    editRectangularNodeApparenceResult,
    (data: GQLEditRectangularNodeApparenceData) => data.editRectangularNodeAppearance
  );

  const [resetRectangularNodeApparence, resetRectangularNodeApparenceResult] = useMutation<
    GQLResetRectangularNodeApparenceData,
    GQLResetRectangularNodeApparenceVariables
  >(GQLResetRectangularNodeAppearanceMutation);

  useReporting(
    resetRectangularNodeApparenceResult,
    (data: GQLResetRectangularNodeApparenceData) => data.resetRectangularNodeAppearance
  );

  const updateInsideLabelBold = (labelId: string, isBold: boolean) =>
    editRectangularNodeApparence({
      variables: {
        input: {
          id: crypto.randomUUID(),
          editingContextId,
          representationId,
          nodeId,
          appearance: {
            insideLabel: {
              labelId,
              bold: isBold,
            },
          },
        },
      },
    });

  const resetInsideLabelBold = (labelId: string, currentIsBold: boolean) =>
    resetRectangularNodeApparence({
      variables: {
        input: {
          id: crypto.randomUUID(),
          editingContextId,
          representationId,
          nodeId,
          appearance: {
            insideLabel: {
              labelId,
              bold: currentIsBold,
            },
          },
        },
      },
    });

  const updateBackground = (background: string) =>
    editRectangularNodeApparence({
      variables: {
        input: {
          id: crypto.randomUUID(),
          editingContextId,
          representationId,
          nodeId,
          appearance: {
            background,
          },
        },
      },
    });

  const resetBackground = (currentBackground: string) =>
    resetRectangularNodeApparence({
      variables: {
        input: {
          id: crypto.randomUUID(),
          editingContextId,
          representationId,
          nodeId,
          appearance: {
            background: currentBackground,
          },
        },
      },
    });

  return { updateInsideLabelBold, updateBackground, resetInsideLabelBold, resetBackground };
};
