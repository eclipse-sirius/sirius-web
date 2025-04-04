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
  GQLResetLabelApparenceData,
  GQLResetLabelApparenceVariables,
  GQLResetLabelAppearanceMutation,
  GQLResetNodeApparenceData,
  GQLResetNodeApparenceVariables,
  GQLResetNodeAppearanceMutation,
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

  const [resetNodeApparence, resetNodeApparenceResult] = useMutation<
    GQLResetNodeApparenceData,
    GQLResetNodeApparenceVariables
  >(GQLResetNodeAppearanceMutation);

  useReporting(resetNodeApparenceResult, (data: GQLResetNodeApparenceData) => data.resetNodeAppearance);

  const [resetLabelApparence, resetLabelApparenceResult] = useMutation<
    GQLResetLabelApparenceData,
    GQLResetLabelApparenceVariables
  >(GQLResetLabelAppearanceMutation);

  useReporting(resetLabelApparenceResult, (data: GQLResetLabelApparenceData) => data.resetLabelAppearance);

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

  const resetLabelStyleProperties = (labelId: string, propertiesToReset: string[]) =>
    resetLabelApparence({
      variables: {
        input: {
          id: crypto.randomUUID(),
          editingContextId,
          representationId,
          ownerElementId: nodeId,
          labelId,
          propertiesToReset,
        },
      },
    });

  const resetInsideLabelBold = (labelId: string) => resetLabelStyleProperties(labelId, ['bold']);

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

  const resetNodeStyleProperties = (propertiesToReset: string[]) =>
    resetNodeApparence({
      variables: {
        input: {
          id: crypto.randomUUID(),
          editingContextId,
          representationId,
          nodeId,
          propertiesToReset,
        },
      },
    });

  const resetBackground = () => resetNodeStyleProperties(['background']);

  return {
    updateInsideLabelBold,
    updateBackground,
    resetLabelStyleProperties,
    resetNodeStyleProperties,
    resetInsideLabelBold,
    resetBackground,
  };
};
